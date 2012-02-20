package org.ssor.test;

import org.ssor.Region;
import org.ssor.AtomicService;
import org.ssor.ServiceManager;
import org.ssor.CompositeService;
import org.ssor.RedundantService;
import org.ssor.RegionDistributionManager;
import org.ssor.util.Group;

public class Configurator {

	
	public static void init(Group group){
		
		RegionDistributionManager view = group.getRegionDistributionManager();
		//view.setInterests(1);
		ServiceManager manager = group.getServiceManager();
		try{
		/*
		 * Meta Service Testing  #########################################
		 */
		
		Region region1 = new Region(1, Region.CONFLICT_REGION);
		Region region2 = new Region(2, Region.CONFLICT_REGION);
		Region region3 = new Region(3, Region.CONFLICT_REGION);
		//Service service1 = new Service("org.ssor.test.TestService.caculate", region1, "__test_cluster",new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		AtomicService unService = new RedundantService(null, "org.ssor.test.UndService.random");
		manager.register(unService);
		AtomicService service2 = new AtomicService("org.ssor.test.TestService.count", region1, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
		//###################### undeterministic service
		
		AtomicService service1 = new AtomicService("org.ssor.test.TestService.caculate", region1,
				 new Class<?>[]{Integer.TYPE,Integer.TYPE});
		//###################### undeterministic service
		
		AtomicService service3 = new AtomicService("org.ssor.test.TestService.work", region2,new Class<?>[]{Integer.TYPE,Integer.TYPE});
		AtomicService service4 = new AtomicService("org.ssor.test.TestService.work1", region3, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
	    //service2.setConcurrentDeliverable(true);
		//service3.setConcurrentDeliverable(true);
		//service4.setConcurrentDeliverable(true);
		manager.register( service1);
		manager.register( service2);
		manager.register( service3);
		manager.register( service4);
		/*
		 * Meta Service Testing  #########################################
		 * 
		 * 
		 */
		
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		
		AtomicService trigger = new CompositeService("org.ssor.test.TestService.countAndCaculate", new AtomicService[]{service2});
		manager.register(trigger);
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	public static void initStrongSequentialConsistency(Group group){
		
		RegionDistributionManager view = group.getRegionDistributionManager();
		//view.setInterests(1);
		ServiceManager manager = group.getServiceManager();
		try{
		/*
		 * Meta Service Testing  #########################################
		 */
		
		Region region1 = new Region(1, Region.CONFLICT_REGION);
		Region region2 = new Region(2, Region.CONFLICT_REGION);
		Region region3 = new Region(3, Region.CONFLICT_REGION);
		//Service service1 = new Service("org.ssor.test.TestService.caculate", region1, "__test_cluster",new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		AtomicService unService = new RedundantService(null, "org.ssor.test.UndService.random");
		manager.register(unService);
		AtomicService service2 = new AtomicService("org.ssor.test.TestService.count", region1, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
		//###################### undeterministic service
		
		AtomicService service1 = new AtomicService("org.ssor.test.TestService.caculate", region1,
				 new Class<?>[]{Integer.TYPE,Integer.TYPE});
		//###################### undeterministic service
		
		AtomicService service3 = new AtomicService("org.ssor.test.TestService.work", region1,new Class<?>[]{Integer.TYPE,Integer.TYPE});
		AtomicService service4 = new AtomicService("org.ssor.test.TestService.work1", region1, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
	    //service2.setConcurrentDeliverable(true);
		//service3.setConcurrentDeliverable(true);
		//service4.setConcurrentDeliverable(true);
		manager.register( service1);
		manager.register( service2);
		manager.register( service3);
		manager.register( service4);
		/*
		 * Meta Service Testing  #########################################
		 * 
		 * 
		 */
		
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		
		AtomicService trigger = new CompositeService("org.ssor.test.TestService.countAndCaculate", new AtomicService[]{service2});
		manager.register(trigger);
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	public static void initCausalAndStrongSequentialConsistency(Group group){
		
		RegionDistributionManager view = group.getRegionDistributionManager();
		//view.setInterests(1);
		ServiceManager manager = group.getServiceManager();
		try{
		/*
		 * Meta Service Testing  #########################################
		 */
		
		Region region1 = new Region(1, Region.CONFLICT_REGION);
		Region region2 = new Region(2, Region.CONFLICT_REGION);
		Region region3 = new Region(3, Region.CONFLICT_REGION);
		//Service service1 = new Service("org.ssor.test.TestService.caculate", region1, "__test_cluster",new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		AtomicService unService = new RedundantService(null, "org.ssor.test.UndService.random");
		manager.register(unService);
		AtomicService service2 = new AtomicService("org.ssor.test.TestService.count", region1, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
		//###################### undeterministic service
		
		AtomicService service1 = new AtomicService("org.ssor.test.TestService.caculate", region1,
				 new Class<?>[]{Integer.TYPE,Integer.TYPE});
		//###################### undeterministic service
		
		AtomicService service3 = new AtomicService("org.ssor.test.TestService.work", region1,new Class<?>[]{Integer.TYPE,Integer.TYPE});
		AtomicService service4 = new AtomicService("org.ssor.test.TestService.work1", region1, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
	    //service2.setConcurrentDeliverable(true);
		//service3.setConcurrentDeliverable(true);
		//service4.setConcurrentDeliverable(true);
		service3.addConcurrentDeliverableService(service3);
		service3.addConcurrentDeliverableService(service4);
		service4.addConcurrentDeliverableService(service3);
		service4.addConcurrentDeliverableService(service4);
		manager.register( service1);
		manager.register( service2);
		manager.register( service3);
		manager.register( service4);
		/*
		 * Meta Service Testing  #########################################
		 * 
		 * 
		 */
		
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		
		AtomicService trigger = new CompositeService("org.ssor.test.TestService.countAndCaculate", new AtomicService[]{service2});
		manager.register(trigger);
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	
	public static void initStrongSequentialConsistencyForThreeRegions(Group group){
		
		RegionDistributionManager view = group.getRegionDistributionManager();
		//view.setInterests(1);
		ServiceManager manager = group.getServiceManager();
		try{
		/*
		 * Meta Service Testing  #########################################
		 */
		
		Region region1 = new Region(1, Region.CONFLICT_REGION);
		Region region2 = new Region(2, Region.CONFLICT_REGION);
		Region region3 = new Region(3, Region.CONFLICT_REGION);
		//Service service1 = new Service("org.ssor.test.TestService.caculate", region1, "__test_cluster",new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		AtomicService unService = new RedundantService(null, "org.ssor.test.UndService.random");
		manager.register(unService);
		AtomicService service2 = new AtomicService("org.ssor.test.TestService.count", region1, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
		//###################### undeterministic service
		
		AtomicService service1 = new AtomicService("org.ssor.test.TestService.caculate", region1,
				 new Class<?>[]{Integer.TYPE,Integer.TYPE});
		//###################### undeterministic service
		
		AtomicService service3 = new AtomicService("org.ssor.test.TestService.work", region2,new Class<?>[]{Integer.TYPE,Integer.TYPE});
		AtomicService service4 = new AtomicService("org.ssor.test.TestService.work1", region3, new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
	    //service2.setConcurrentDeliverable(true);
		//service3.setConcurrentDeliverable(true);
		//service4.setConcurrentDeliverable(true);
		manager.register( service1);
		manager.register( service2);
		manager.register( service3);
		manager.register( service4);
		/*
		 * Meta Service Testing  #########################################
		 * 
		 * 
		 */
		
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		
		AtomicService trigger = new CompositeService("org.ssor.test.TestService.countAndCaculate", new AtomicService[]{service2});
		manager.register(trigger);
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	
	public static void initSequentialConsistency(Group group){
		
		RegionDistributionManager view = group.getRegionDistributionManager();
		//view.setInterests(1);
		ServiceManager manager = group.getServiceManager();
		try{
		/*
		 * Meta Service Testing  #########################################
		 */
		
		Region region1 = new Region(1, Region.CONFLICT_REGION);
		Region region2 = new Region(2, Region.CONFLICT_REGION);
		Region region3 = new Region(3, Region.CONFLICT_REGION);
		//Service service1 = new Service("org.ssor.test.TestService.caculate", region1, "__test_cluster",new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		AtomicService unService = new RedundantService(null, "org.ssor.test.UndService.random");
		manager.register(unService);
		AtomicService service2 = new AtomicService("org.ssor.test.TestService.count", manager.getSessionRegion(), new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
		//###################### undeterministic service
		
		AtomicService service1 = new AtomicService("org.ssor.test.TestService.caculate", region1,
				 new Class<?>[]{Integer.TYPE,Integer.TYPE});
		//###################### undeterministic service
		
		AtomicService service3 = new AtomicService("org.ssor.test.TestService.work", manager.getSessionRegion(),new Class<?>[]{Integer.TYPE,Integer.TYPE});
		AtomicService service4 = new AtomicService("org.ssor.test.TestService.work1", manager.getSessionRegion(), new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
	    //service2.setConcurrentDeliverable(true);
		//service3.setConcurrentDeliverable(true);
		//service4.setConcurrentDeliverable(true);
		manager.register( service1);
		manager.register( service2);
		manager.register( service3);
		manager.register( service4);
		/*
		 * Meta Service Testing  #########################################
		 * 
		 * 
		 */
		
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		
		AtomicService trigger = new CompositeService("org.ssor.test.TestService.countAndCaculate", new AtomicService[]{service2});
		manager.register(trigger);
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	
	public static void initCausalAndSequentialConsistency(Group group){
		
		RegionDistributionManager view = group.getRegionDistributionManager();
		//view.setInterests(1);
		ServiceManager manager = group.getServiceManager();
		try{
		/*
		 * Meta Service Testing  #########################################
		 */
		
		Region region1 = new Region(1, Region.CONFLICT_REGION);
		Region region2 = new Region(2, Region.CONFLICT_REGION);
		Region region3 = new Region(3, Region.CONFLICT_REGION);
		//Service service1 = new Service("org.ssor.test.TestService.caculate", region1, "__test_cluster",new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		AtomicService unService = new RedundantService(null, "org.ssor.test.UndService.random");
		manager.register(unService);
		AtomicService service2 = new AtomicService("org.ssor.test.TestService.count", manager.getSessionRegion(), new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
		//###################### undeterministic service
		
		AtomicService service1 = new AtomicService("org.ssor.test.TestService.caculate", region1,
				 new Class<?>[]{Integer.TYPE,Integer.TYPE});
		//###################### undeterministic service
		
		AtomicService service3 = new AtomicService("org.ssor.test.TestService.work", manager.getSessionRegion(),new Class<?>[]{Integer.TYPE,Integer.TYPE});
		AtomicService service4 = new AtomicService("org.ssor.test.TestService.work1", manager.getSessionRegion(), new Class<?>[]{Integer.TYPE,Integer.TYPE});
		
		
	    //service2.setConcurrentDeliverable(true);
		//service3.setConcurrentDeliverable(true);
		//service4.setConcurrentDeliverable(true);
		manager.register( service1);
		manager.register( service2);
		manager.register( service3);
		manager.register( service4);
		/*
		 * Meta Service Testing  #########################################
		 * 
		 * 
		 */
		
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		
		AtomicService trigger = new CompositeService("org.ssor.test.TestService.countAndCaculate", new AtomicService[]{service2});
		manager.register(trigger);
		
		/*
		 * Trigger Service Testing  #########################################
		 */
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
