package com.example.study;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.example.study.config.KafkaProducer;
import com.example.study.entity.UsedUrlInfo;
import com.example.study.mapper.UrlInfoMapper;
import com.example.study.mapper.UrlRelationMapper;
import com.example.study.mapper.UrlUsedMapper;
import com.example.study.utils.CustomAggregationOperationHandler;
import org.apache.commons.lang.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class StudyApplicationTests {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	KafkaProducer kafkaProducer;
	@Autowired
	UrlInfoMapper urlInfoMapper;
	@Autowired
	UrlRelationMapper urlRelationMapper;
	@Resource
	UrlUsedMapper urlUsedMapper;

	@Test
	void mongoAggregationTest() {
		Aggregation aggregation=Aggregation.newAggregation(
//				Aggregation.match(new Criteria().and("actionID").is("A20220727145021")),
//				Aggregation.lookup("TB_StageConfig","actionID","actionID","stages"),
//				Aggregation.lookup("TB_TaskConfig","stages.stageID","stageID","tasks"),
				new CustomAggregationOperationHandler("{\n" +
						"$match:{$and:[{\"actionID\":\"A20220727145021\"}]}\n" +
						"},\n" +
						"{\n" +
						"    $lookup: {\n" +
						"      from: \"TB_StageConfig\",\n" +
						"      let: {\n" +
						"        stages: \"$stages\",\n" +
						"\t\t\t\tactionID:\"$actionID\"\n" +
						"      },\n" +
						"      pipeline: [\n" +
						"        {\n" +
						"          $match: {\n" +
						"            $expr: {\n" +
						"              $eq: [\n" +
						"                \"$actionID\",\n" +
						"                \"$$actionID\"\n" +
						"              ]\n" +
						"            }\n" +
						"          }\n" +
						"        },\n" +
						"        {\n" +
						"          $lookup: {\n" +
						"            from: \"TB_TaskConfig\",\n" +
						"            let: {\n" +
						"              tasks: \"$tasks\",\n" +
						"\t\t\t\t\t\t\tstageID:\"$stageID\"\n" +
						"            },\n" +
						"            pipeline: [\n" +
						"              {\n" +
						"                $match: {\n" +
						"                  $expr: {\n" +
						"                    $eq: [\n" +
						"                      \"$stageID\",\n" +
						"                      \"$$stageID\"\n" +
						"                    ]\n" +
						"                  }\n" +
						"                }\n" +
						"              },\n" +
						"//              {\n" +
						"//                $lookup: {\n" +
						"//                  from: \"countries\",\n" +
						"//                  localField: \"nationality\",\n" +
						"//                  foreignField: \"_id\",\n" +
						"//                  as: \"nationality\"\n" +
						"//                }\n" +
						"//              }\n" +
						"            ],\n" +
						"            as: \"tasks\"\n" +
						"          }\n" +
						"        }\n" +
						"      ],\n" +
						"      as: \"stages\"\n" +
						"    }\n" +
						"  }")
		);
		AggregationResults<Map> aggregateIterable=mongoTemplate.aggregate(aggregation,"TB_ActiveConfig", Map.class);
		System.out.println(JSON.toJSONString(aggregateIterable.getMappedResults()));
	}

	@Test
	void kafkaTest() {
		kafkaProducer.sendMessage("xiaoxi");
	}

	@Test
	void url() throws InterruptedException {
		String beginUrlId="1";
		long begin=System.currentTimeMillis();

		//初始化数据
		List<UsedUrlInfo> list=urlUsedMapper.selectUsedUrl(beginUrlId);
		for(UsedUrlInfo usedUrlInfo:list){
			if(StringUtils.isEmpty(usedUrlInfo.getPid())) usedUrlInfo.setPid("0");
		}
		list.add(new UsedUrlInfo("0",null,"url0"));

		ConcurrentHashMap<String, ConcurrentHashSet<String>> children_parentMap=new ConcurrentHashMap<>();
		ConcurrentHashMap<String,ConcurrentHashSet<String>> parent_childrenMap=new ConcurrentHashMap<>();

		ConcurrentHashMap<String,ConcurrentHashMap<String,List<Object>>> responseDataMap=new ConcurrentHashMap<>();
		ConcurrentHashMap<String, ConcurrentHashMap<String, List<Object>>> requestParamMap=new ConcurrentHashMap<>();

		ConcurrentHashMap<String, Object> waitAndNotifyMap=new ConcurrentHashMap<>();

		List<Object> lastResponse=Collections.synchronizedList(new ArrayList<>());
		List<Object> beginRequest=Collections.synchronizedList(new ArrayList<>());

		for(UsedUrlInfo usedUrlInfo:list){

			String id=usedUrlInfo.getUsedUrlId();
			String pid=usedUrlInfo.getPid();

			if(children_parentMap.containsKey(id)&&!StringUtils.isEmpty(pid))
				children_parentMap.get(id).add(pid);
			else {
				ConcurrentHashSet<String> pList=new ConcurrentHashSet<>();
				if(!StringUtils.isEmpty(pid))pList.add(pid);
				children_parentMap.put(id,pList);
			}

			if(!StringUtils.isEmpty(pid)){
				if(parent_childrenMap.containsKey(pid))
					parent_childrenMap.get(pid).add(id);
				else {
					ConcurrentHashSet<String> cList=new ConcurrentHashSet<>();
					cList.add(id);
					parent_childrenMap.put(pid,cList);
				}
			}

			if(responseDataMap.containsKey(id)&&!StringUtils.isEmpty(pid))
				responseDataMap.get(id).put(pid,Collections.synchronizedList(new ArrayList<>()));
			else {
				ConcurrentHashMap<String,List<Object>> pResponseMap=new ConcurrentHashMap<>();
				if(!StringUtils.isEmpty(pid))pResponseMap.put(pid,Collections.synchronizedList(new ArrayList<>()));
				responseDataMap.put(id,pResponseMap);
			}

			if(!StringUtils.isEmpty(pid)){
				if(requestParamMap.containsKey(pid))
					requestParamMap.get(pid).put(id,Collections.synchronizedList(new ArrayList<>()));
				else {
					ConcurrentHashMap<String,List<Object>> cRequestMap=new ConcurrentHashMap<>();
					cRequestMap.put(id,Collections.synchronizedList(new ArrayList<>()));
					requestParamMap.put(pid,cRequestMap);
				}
			}
		}

		//用于操作的map,需要深拷贝
		ConcurrentHashMap<String, ConcurrentHashSet<String>> children_parentMap_execute=(ConcurrentHashMap<String, ConcurrentHashSet<String>>)SerializationUtils.clone(children_parentMap);
		ConcurrentHashMap<String,ConcurrentHashSet<String>> parent_childrenMap_execute=(ConcurrentHashMap<String, ConcurrentHashSet<String>>)SerializationUtils.clone(parent_childrenMap);

		//todo：初始化的时候添加一个唯一根结点方便返回

		for(Map.Entry<String, ConcurrentHashSet<String>> set:children_parentMap.entrySet()){
			//检查所有父list size为0的url可以并发执行
			if(set.getValue().size()==0){
				urlExecute(set.getKey(),parent_childrenMap,children_parentMap,requestParamMap,responseDataMap,parent_childrenMap_execute,children_parentMap_execute,waitAndNotifyMap,lastResponse,begin);
			}
		}

		Object mainLock=new Object();
		synchronized (mainLock){
			waitAndNotifyMap.put("main",mainLock);
			System.out.println("主进程阻塞");
			mainLock.wait();
			System.out.println("主进程恢复运行");
		}
	}

	public static void urlExecute(String urlId,
								  ConcurrentHashMap<String, ConcurrentHashSet<String>> parent_childrenMap,
								  ConcurrentHashMap<String, ConcurrentHashSet<String>> children_parentMap,
								  ConcurrentHashMap<String, ConcurrentHashMap<String, List<Object>>> requestParamMap,
								  ConcurrentHashMap<String, ConcurrentHashMap<String, List<Object>>> responseDataMap,
								  ConcurrentHashMap<String, ConcurrentHashSet<String>> parent_childrenMap_execute,
								  ConcurrentHashMap<String, ConcurrentHashSet<String>> children_parentMap_execute,
								  ConcurrentHashMap<String, Object> waitAndNotifyMap,
								  List<Object> lastResponse,
								  long beginTime){

		CompletableFuture<String> future=CompletableFuture.supplyAsync(()->{
			try {

				/**
				 * 父参数获取和子参数设置阶段
				 */

				ConcurrentHashSet<String> cList=parent_childrenMap.get(urlId);
				ConcurrentHashSet<String> pList=children_parentMap.get(urlId);
				if(cList==null) cList=new ConcurrentHashSet<>();
				//拿所有的父级参数
				StringBuilder parentParam=new StringBuilder("");
				for(String pid:pList){
					parentParam.append(requestParamMap.get(pid).get(urlId));
				}
				//给所有的子设置参数
				for(String cid:cList){
					requestParamMap.get(urlId).get(cid).add(urlId+"参数!");
				}
				System.out.println("参数列表:"+urlId + "参数!" + parentParam);

				/**
				 * 执行阶段1
				 */

				System.out.println(Thread.currentThread().getName()+"执行了:"+urlId+"的一阶段");

				/**
				 * 阻塞阶段
				 */

				//检查该url的子list size，为0则可以返回响应数据，否则需要wait()
				if(parent_childrenMap_execute.get(urlId)!=null&&parent_childrenMap_execute.get(urlId).size()!=0){
					//将自己从所有的子的children_parentMap_execute中移除
					for(String cid:cList){
						children_parentMap_execute.get(cid).remove(urlId);
						if(children_parentMap_execute.get(cid).size()==0){
							urlExecute(cid,parent_childrenMap,children_parentMap,requestParamMap,responseDataMap,parent_childrenMap_execute,children_parentMap_execute,waitAndNotifyMap,lastResponse,beginTime);
						}
					}
					//继续运行
					//添加该url的唤醒实例，由最后一个执行完的子来唤醒
					Object o=new Object();
					synchronized (o){
						waitAndNotifyMap.put(urlId,o);
						System.out.println(urlId+"运行到阻塞");
						o.wait(2000);
					}
					//继续运行
					System.out.println(urlId+"阻塞解除");
				}

				/**
				 * 执行阶段2
				 */

				System.out.println(Thread.currentThread().getName()+"执行了:"+urlId+"的二阶段");

				/**
				 * 子响应数据获取阶段和父响应数据设置阶段
				 */

				//拿到所有子的响应数据
				StringBuilder childrenData=new StringBuilder("");
				for(String cid:cList){
					childrenData.append(responseDataMap.get(cid).get(urlId));
				}

				//给所有父存储响应数据
				//size=0说明没有父，该url的响应结果直接作为整体响应的一部分
				//这里只会在根节点执行
				if(pList.size()==0){
					lastResponse.add(urlId+"执行结果!"+childrenData);
					//拿到待唤醒的父线程的锁对象
					Object o=waitAndNotifyMap.get("main");
					while (o==null) {
						//如果待唤醒的父线程的锁对象还没有加入map就先睡0.5s
						System.out.println("主进程锁还未设置，等待");
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						o=waitAndNotifyMap.get("main");
					}
					synchronized (o){
						System.out.println(urlId+"唤醒主进程");
						o.notify();
					}
				}
				//否则就存进所有的父map中
				else {
					for(String pid:pList){
						//唤醒时也需要开多线程,否则一个阻塞会导致其他的也阻塞
						new Thread(()->{
							responseDataMap.get(urlId).get(pid).add(urlId+"执行结果!"+childrenData);
							parent_childrenMap_execute.get(pid).remove(urlId);
							if(parent_childrenMap_execute.get(pid).size()==0){
								//拿到待唤醒的父线程的锁对象
								Object o=waitAndNotifyMap.get(pid);
								while (o==null) {
									//如果待唤醒的父线程的锁对象还没有加入map就先睡0.5s
									System.out.println(urlId+"父锁"+pid+"还未设置，等待");
									try {
										TimeUnit.MILLISECONDS.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									o=waitAndNotifyMap.get(pid);
								}
								synchronized (o){
									System.out.println(urlId+"唤醒"+pid);
									o.notify();
								}
							}
						}).start();
					}
				}
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			return urlId+" complete!"+lastResponse+(System.currentTimeMillis()-beginTime);
		});
		future.whenComplete((item,ex)->{
			System.out.println(item);
		});
	}

}
