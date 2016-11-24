package com.petclinic.config;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

	@Bean
	TcpDiscoveryMulticastIpFinder ipFinder(){
		TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
		ipFinder.setMulticastGroup("229.11.14.160");
		return ipFinder;
	}
	
	@Autowired
	TcpDiscoveryMulticastIpFinder ipFinder;
	
	@Bean
	public TcpDiscoverySpi discoverySpi(){
		TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
		discoverySpi.setIpFinder(ipFinder);
		return discoverySpi;
	}

	@Autowired
	TcpDiscoverySpi discoverySpi;
	
    @Bean
    @SuppressWarnings("unchecked")
    public org.apache.ignite.cache.spring.SpringCacheManager cacheManager() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setGridName("petclinic-ignite-grid");
        igniteConfiguration.setDiscoverySpi(discoverySpi);
        //igniteConfiguration.setClassLoader(dynamicClassLoaderWrapper());

        igniteConfiguration.setCacheConfiguration(this.createDefaultCache("petclinic"),
                this.createDefaultCache("org.hibernate.cache.spi.UpdateTimestampsCache"),
                this.createDefaultCache("org.hibernate.cache.internal.StandardQueryCache"));

        SpringCacheManager springCacheManager = new SpringCacheManager();
        springCacheManager.setConfiguration(igniteConfiguration);
        springCacheManager.setDynamicCacheConfiguration(this.createDefaultCache(null));
        return springCacheManager;
    }

    private org.apache.ignite.configuration.CacheConfiguration createDefaultCache(String name) {
        
    	org.apache.ignite.configuration.CacheConfiguration cacheConfiguration = new org.apache.ignite.configuration.CacheConfiguration();
        cacheConfiguration.setName(name);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheConfiguration.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        cacheConfiguration.setStatisticsEnabled(true);
        cacheConfiguration.setEvictSynchronized(true);
        return cacheConfiguration;
    }
}