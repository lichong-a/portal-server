/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@ConfigurationProperties(prefix = "application.cos")
@Configuration
public class COSConfig {

    /**
     * 腾讯云控制台项目配置secretId
     */
    private String secretId;

    /**
     * 腾讯云控制台项目配置secretKey
     */
    private String secretKey;
    /**
     * 存储桶地域
     */
    private String region;
    /**
     * 存储桶名称
     */
    private String bucketName = "ielts-online-1312690188";
    /**
     * 业务项目名称
     */
    private String projectName = "ielts-online";
    /**
     * 公共目录
     */
    private String common = "common";
    /**
     * 图片大小
     */
    private String imageSize = "2";
    /**
     * CDN加速域名
     */
    private String prefixDomain = "";

    /**
     * 创建 COSClient 实例，这个实例用来后续调用请求
     *
     * @return COSClient
     */
    @Bean
    public COSClient createCOSClient() {
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参见 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(region));

        // 以下的设置，是可选的：

        // 设置 socket 读取超时，默认 30s
        // clientConfig.setSocketTimeout(30*1000);
        // 设置建立连接超时，默认 30s
        // clientConfig.setConnectionTimeout(30*1000);

        // 如果需要的话，设置 http 代理，ip 以及 port
        // clientConfig.setHttpProxyIp("httpProxyIp");
        // clientConfig.setHttpProxyPort(80);

        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    /**
     * 创建 TransferManager 实例，这个实例用来后续调用高级接口
     */
    @Bean
    public TransferManager createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();

        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(32);

        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        return new TransferManager(cosClient, threadPool);
    }
}
