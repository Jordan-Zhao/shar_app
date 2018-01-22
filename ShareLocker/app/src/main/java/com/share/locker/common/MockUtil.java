package com.share.locker.common;

/**
 * Created by Jordan on 22/01/2018.
 */

public class MockUtil {
    //暂时MOCK,应该根据lockerSize和“我的位置”去服务端取数据
    public static MachineVO[] getMatchedMachine(int i){
        MachineVO[] arr = new MachineVO[2];
        MachineVO vo1 = new MachineVO();
        vo1.setId(1);
        vo1.setAddress("浙大东门");

        MachineVO vo2 = new MachineVO();
        vo2.setId(2);
        vo2.setAddress("江晖路16号");

        MachineVO vo3 = new MachineVO();
        vo3.setId(3);
        vo3.setAddress("浙大西门");
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
    }
}
