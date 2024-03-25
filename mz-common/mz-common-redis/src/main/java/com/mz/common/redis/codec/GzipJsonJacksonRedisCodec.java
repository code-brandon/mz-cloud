package com.mz.common.redis.codec;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.JsonJacksonCodec;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 小政同学 it_xiaozheng@163.com
 * @date 2024-02-29 上午11:57
 */
public class GzipJsonJacksonRedisCodec extends JsonJacksonCodec {

    private final Encoder encoder = in -> {
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        try {
            ByteBufOutputStream os = new ByteBufOutputStream(out);
            // 对象转JSON
            mapObjectMapper.writeValue((OutputStream) os, in);
            // JSON输出流 转 字节输入流
            ByteBufInputStream bin = new ByteBufInputStream(os.buffer());
            // 字节输入流 Gzip 压缩 为 字节数组
            byte[] gzip = ZipUtil.gzip(bin);
            // 字节数组 转为 字节输入流
            ByteArrayInputStream gzipInStream = IoUtil.toStream(gzip);
            // 字节输入流 copy 字节输出流
            IoUtil.copy(gzipInStream, os);
            // 关闭 字节输入流
            gzipInStream.close();
            // 输出
            return os.buffer();
        } catch (IOException e) {
            out.release();
            throw e;
        } catch (Exception e) {
            out.release();
            throw new IOException(e);
        }
    };


    private final Decoder<Object> decoder = (buf, state) -> {
        ByteBufInputStream in = new ByteBufInputStream(buf);
        byte[] bytes = ZipUtil.unGzip(in);
        return mapObjectMapper.readValue(bytes, Object.class);
    };

    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }
}
