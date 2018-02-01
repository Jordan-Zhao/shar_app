package com.share.locker.common;

/**
 * Created by Jordan on 22/01/2018.
 */

public class MockUtil {
    /**
     * 机柜判断lockerId是否自己的柜门
     */
    public static boolean isContainLocker(Long lockerId){
        return true;
    }

    public static void openLocker(Long lockerId){

    }

    //暂时MOCK,应该根据lockerSize和“我的位置”去服务端取数据
   /* public static SelectableLockerVO[] getMatchedMachine(int i){
        SelectableLockerVO[] arr = new SelectableLockerVO[2];
        SelectableLockerVO vo1 = new SelectableLockerVO();
        vo1.setLockerId(1);
        vo1.setMachineName("浙大东门1号柜");

        SelectableLockerVO vo2 = new SelectableLockerVO();
        vo2.setLockerId(2);
        vo2.setMachineName("江晖路16号柜");

        SelectableLockerVO vo3 = new SelectableLockerVO();
        vo3.setLockerId(3);
        vo3.setMachineName("浙大西门3号柜");
        if(i == 0) {    //小柜
            arr[0] = vo1;
            arr[1] = vo2;
        }else if(i == 1){
            arr[0] = vo2;
            arr[1] = vo3;
        }else if(i == 2){
            arr[0] = vo3;
            arr[1] = vo1;
        }
        return arr;
    }*/
}
