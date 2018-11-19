package com.ljh.qlexpress.test;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiahan
 * @Title: TestQLExpress
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/16
 * @ModifiedBy:
 */
public class TestQLExpress {
    private static ExpressRunner runner = new ExpressRunner();

    /**
     * 判断一个用户tag的第x位是否为1
     * @param userInfo
     * @param tagBitIndex
     * @return
     */
    public boolean userTagJudge(UserInfo userInfo,int tagBitIndex){
        boolean r = (userInfo.getTag() & ((long)Math.pow(2,tagBitIndex)))>0;
        return r;
    }

    /**
     * 判断用户是否购买过商品
     * @param userInfo
     * @param goodsId
     * @return
     */
    public boolean hasOrderGoods(UserInfo userInfo,long goodsId){
        if(userInfo.getId()%2==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 初始化语句执行器
     */
    public void initial() throws Exception {
        runner.addOperatorWithAlias("而且","and",null);
        runner.addFunctionOfClassMethod("userTagJudge", TestQLExpress.class.getName(), "userTagJudge",
                new String[] {UserInfo.class.getName(),"int"}, "你不是三星卖家");
        runner.addFunctionOfClassMethod("hasOrderGoods", TestQLExpress.class.getName(), "hasOrderGoods",
                new String[] {UserInfo.class.getName(),"long"}, "你没有开通淘宝店铺");
        runner.addMacro("三星卖家", "userTagJudge(userInfo,3)");//3表示三星卖家的标志位
        runner.addMacro("已经开店", "hasOrderGoods(userInfo,100)");//100表示旺铺商品的ID

    }

    /**
     * 判断逻辑执行函数
     * @param userInfo
     * @param expression
     * @return
     * @throws Exception
     */
    public String hasPermission(UserInfo userInfo,String expression) throws Exception {
        IExpressContext<String,Object> expressContext = new DefaultContext<String,Object>();
        expressContext.put("userInfo",userInfo);
        List<String> errorInfo = new ArrayList<String>();
        Boolean result = (Boolean)runner.execute(expression, expressContext, errorInfo, true, false);
        String resultStr ="";
        if(result.booleanValue() == true){
            resultStr = "可以订购此商品";
        }else{
            for(int i=0;i<errorInfo.size();i++){
                if(i > 0){
                    resultStr  = resultStr + "," ;
                }
                resultStr  = resultStr + errorInfo.get(i);
            }
            resultStr = resultStr  + ",所以不能订购此商品";
        }
        return "亲爱的" + userInfo.getName() + " : " + resultStr;
    }

    public static void main(String[] args) throws Exception {
        TestQLExpress demo = new TestQLExpress();
        demo.initial();
        System.out.println(demo.hasPermission(new UserInfo(100,"xuannan",7),  "三星卖家   而且   已经开店"));
        System.out.println(demo.hasPermission(new UserInfo(101,"qianghui",8), "三星卖家   而且   已经开店"));
        System.out.println(demo.hasPermission(new UserInfo(100,"张三",8), "三星卖家 and 已经开店"));
        System.out.println(demo.hasPermission(new UserInfo(100,"李四",7), "三星卖家 and 已经开店"));

    }
}
