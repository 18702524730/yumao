package com.xsebe.yumao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xsebe.api.docking.alibaba.alipay.SignUtil;
import com.xsebe.api.utility.HttpUtility;
import com.xsebe.api.utility.HttpUtility.Parameter;
import com.xsebe.yumao.AbstractTest;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainNodeOriginalWorks;
import com.xsebe.yumao.service.EvidChainNodeOriginalWorksService;

/** 
 * @Description:TODO  
 * @date:   2018年8月20日 下午5:21:11 
 * @ClassName:  周伯通 
 */
public class AlipayControllerTest  extends AbstractTest {
    
    public static void main(String[] args) {
        
        System.out.println("INSERT INTO user_realname_auth (id,user_id,ent_name,ent_bank_name,ent_bank_no_dict_value,ent_bank_public_account_no,ent_id_picture,status,failure_cause) VALUES ('" + UUID.randomUUID().toString().replace("-", "") + "','edd8711ccad24987bc2c47a2fa218baf','佛山市嘻妞户外家具有限公司',NULL,NULL,'91440606398144118Q',NULL,3,'')");
    }
    
    @Test
    public void testOpenNotifyURLVSign() {
        
        List<Parameter> params = new ArrayList<HttpUtility.Parameter>();
        params.add(new Parameter("discount", "0.00"));
        params.add(new Parameter("paymentType", "1"));
        params.add(new Parameter("subject", "香皂"));
        params.add(new Parameter("buyerEmail", "111@qq5.5uncmom"));
        String sign = SignUtil.sign(params, "e6jt7uvr3u8wmdrcm24g84kbdnwadosd", "utf-8");
        
        boolean verify = SignUtil.verify(params, sign, "e6jt7uvr3u8wmdrcm24g84kbdnwadosd",  "utf-8");
        System.out.println(verify);
        
        
        
    }
    
    
    /*        //签名
    String sign = SignUtil.sign(params, "", "MD5");
    //验签
    boolean verify2 = SignUtil.verify(params, sign, "", "MD5");
    
    if(verify2){
        //正确
    }else{
        //错误
    }*/
    @Test
    public void test(){
        EvidChainNodeOriginalWorksService service = get(EvidChainNodeOriginalWorksService.class);
        List<EvidChainNodeOriginalWorks> byEvidChainId = null;
        try {
            byEvidChainId = service.getByEvidChainId("465123");
        } catch (YumaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (EvidChainNodeOriginalWorks evidChainNodeOriginalWorks : byEvidChainId) {
            System.out.println(evidChainNodeOriginalWorks.getId());
            System.out.println(evidChainNodeOriginalWorks.getEvidChain().getName());
            System.out.println(evidChainNodeOriginalWorks.getEvidChainCategoryNode().getName());
            System.out.println(evidChainNodeOriginalWorks.getOriginalWorks().getName());
        }
    }
}
