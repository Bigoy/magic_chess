package com.tssss.bysj.base;

import com.tssss.bysj.http.request.host.IHost;

/**
 * Created by tssss on 2019/2/14
 * <p>
 * 管理整个 APP 项目联网会用到的主机地址
 */
public interface HostManager {
    // 百度主机（测试网络框架）
    IHost baiduHost = new IHost() {
        @Override
        public String getHost() {
            return "https://www.baidu.com";
        }

        @Override
        public String getDefaultPath() {
            return "";
        }
    };

    // 自己的服务器主机地址（暂无）
   /* IHost magicChessHost = new IHost() {
        @Override
        public String getHost() {
            return "www.tssss.com";
        }

        @Override
        public String getDefaultPath() {
            return "MagicChess";
        }
    };*/
}
