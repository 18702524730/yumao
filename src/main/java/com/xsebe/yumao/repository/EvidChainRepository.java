package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.EvidChain;
import com.xsebe.yumao.model.User;

@Repository
public interface EvidChainRepository {

    long getTotalByUser(@Param("current") User current, @Param("name") String name);

    List<EvidChain> getPageLimit(@Param("current") User current, @Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);

    void add(@Param("evidChain") EvidChain evidChain);

    EvidChain get(@Param("id") String id);

    void delete(@Param("id") String id);

    void updatePercent(@Param("chainId") String chainId, @Param("percent") float percent);
}
