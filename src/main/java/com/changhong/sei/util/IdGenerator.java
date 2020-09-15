package com.changhong.sei.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <strong>实现功能:</strong>.
 * <p>
 * 封装各种生成唯一性ID算法的工具类.
 * 1.生产UUID
 * 2.生产随机数
 * </p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/5/4 14:07
 */
public class IdGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(IdGenerator.class);

    private static final Pattern PATTERN_LONG_ID = Pattern.compile("^([0-9]{15})([0-9a-f]{32})([0-9a-f]{3})$");

    private static final Pattern PATTERN_HOSTNAME = Pattern.compile("^.*\\D+([0-9]+)$");

    private static final long OFFSET = LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.of("Z")).toEpochSecond();

    // 最大6.5万个序列号
    private static final long MAX_NEXT = 0b11111_11111111_111L;

    private static final long SHARD_ID = getServerIdAsLong();

    private static long offset = 0;

    private static long lastEpoch = 0;

    /**
     * 53bitID由32bit秒级时间戳+16bit自增+5bit机器标识组成，累积32台机器，每秒可以生成6.5万个序列号
     * 机器标识采用简单的主机名方案，只要主机名符合host-1，host-2就可以自动提取机器标识，无需配置
     *
     * @return 返回53位整型数值.避免JavaScript的精度丢失
     */
    public static long nextId() {
        return nextId(System.currentTimeMillis() / 1000);
    }

    private static synchronized long nextId(long epochSecond) {
        if (epochSecond < lastEpoch) {
            // warning: clock is turn back:
            LOG.warn("clock is back: " + epochSecond + " from previous:" + lastEpoch);
            epochSecond = lastEpoch;
        }
        if (lastEpoch != epochSecond) {
            lastEpoch = epochSecond;
            reset();
        }
        offset++;
        long next = offset & MAX_NEXT;
        if (next == 0) {
            LOG.warn("maximum id reached in 1 second in epoch: " + epochSecond);
            // 如果达到每秒6.5万个序列号,可以继续递增时间戳，向前“借”下一秒的6.5万个序列号
            return nextId(epochSecond + 1);
        }
        return generateId(epochSecond, next, SHARD_ID);
    }

    private static void reset() {
        offset = 0;
    }

    private static long generateId(long epochSecond, long next, long shardId) {
        return ((epochSecond - OFFSET) << 21) | (next << 5) | shardId;
    }

    private static long getServerIdAsLong() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            Matcher matcher = PATTERN_HOSTNAME.matcher(hostname);
            if (matcher.matches()) {
                long n = Long.parseLong(matcher.group(1));
                if (n >= 0 && n < 8) {
                    LOG.info("detect server id from host name {}: {}.", hostname, n);
                    return n;
                }
            }
        } catch (UnknownHostException e) {
            LOG.warn("unable to get host name. set server id = Random[8, 32].");
        }
        // 随机产生一个8-32的数值
        return RandomUtils.getInteger(8, 32);
    }

    public static long stringIdToLongId(String stringId) {
        // a stringId id is composed as timestamp (15) + uuid (32) + serverId (000~fff).
        Matcher matcher = PATTERN_LONG_ID.matcher(stringId);
        if (matcher.matches()) {
            long epoch = Long.parseLong(matcher.group(1)) / 1000;
            String uuid = matcher.group(2);
            byte[] sha1 = HashUtil.sha1AsBytes(uuid);
            long next = ((sha1[0] << 24) | (sha1[1] << 16) | (sha1[2] << 8) | sha1[3]) & MAX_NEXT;
            long serverId = Long.parseLong(matcher.group(3), 16);
            return generateId(epoch, next, serverId);
        }
        throw new IllegalArgumentException("Invalid id: " + stringId);
    }

    ///////////////////////// UUID生成 /////////////////////////////

    private static SecureRandom random = new SecureRandom();

    // different ProcessEngines on the same classloader share one generator.
    private static TimeBasedGenerator timeBasedGenerator;

    private static void ensureGeneratorInitialized() {
        if (timeBasedGenerator == null) {
            synchronized (IdGenerator.class) {
                if (timeBasedGenerator == null) {
                    timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
                }
            }
        }
    }

    /**
     * 封装fasterxml.uuid,生成有序的UUID.
     *
     * @return 返回UUID，中间有-分割
     */
    public static String uuid() {
        ensureGeneratorInitialized();
        return timeBasedGenerator.generate().toString().toUpperCase();
    }

    /**
     * 封装fasterxml.uuid,生成有序的UUID.
     *
     * @return 返回UUID, 中间无-分割.
     */
    public static String uuid2() {
        ensureGeneratorInitialized();
        return timeBasedGenerator.generate().toString().toUpperCase().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     * 不保证唯一性
     *
     * @return 随机数
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        for (int i = 1; i < 100; i++) {
            System.out.println(IdGenerator.nextId());
//            System.out.println(IdGenerator.uuid());
//            System.out.println(IdGenerator.uuid2());
//            System.out.println(IdGenerator.randomLong());
//            System.out.println();
        }


        for (int m = 1; m < 100; m++) {
            CompletableFuture.runAsync(() -> {
                for (int i = 1; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "    " + IdGenerator.nextId());
//            System.out.println(IdGenerator.uuid());
//            System.out.println(IdGenerator.uuid2());
//            System.out.println(IdGenerator.randomLong());
//            System.out.println();
                }
            });
        }

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("#####################");
        System.out.println();

//        for (int i = 0; i < 65534; i++) {
//            IdGenerator.nextId();
//        }
//        // nextId: 1111111111111111xxxxx:
//        System.out.println(Objects.equals(0b1111111111111111_00000L, IdGenerator.nextId() & 0b1111111111111111_00000L));
//        System.out.println(Objects.equals(0b01_00000L, IdGenerator.nextId() & 0b1111111111111111_00000L));
//        System.out.println(Objects.equals(0b10_00000L, IdGenerator.nextId() & 0b1111111111111111_00000L));
//
//        System.out.println();
//        System.out.println("#####################");
//        System.out.println();

//        System.out.println(Objects.equals(895882413934592L, IdGenerator.stringIdToLongId("00160013403949375AF7F5DF6F411EAA36152730A9A7916000")));
//        System.out.println(Objects.equals(895882413934592L, IdGenerator.stringIdToLongId("0013738748248885ddf38d8cd1b4803aa74bcda32f853fd000")));
//        System.out.println(Objects.equals(895884706212896L, IdGenerator.stringIdToLongId("001373875917148f989cdeb2b27441d95112edb37834a0b000")));
//        System.out.println(Objects.equals(969955749132672L, IdGenerator.stringIdToLongId("001409195742008d822b26cf3de46aea14f2b7378a1ba91000")));
//        System.out.println(Objects.equals(1023020745357888L, IdGenerator.stringIdToLongId("0014344991049250a2c80ec84cb4861bbd1d9b2c0c2850e000")));
//        System.out.println(Objects.equals(1258830748973376L, IdGenerator.stringIdToLongId("001546942076610f8e8bc7218ed4a7eb597ed2658047a17000")));
//        System.out.println(Objects.equals(1270783617794016L, IdGenerator.stringIdToLongId("0015526416484437ca3e4b736e54075a9d5fec66c691a6e000")));
        System.out.println(IdGenerator.stringIdToLongId("00" + System.currentTimeMillis() + IdGenerator.uuid2().toLowerCase() + "000"));
    }
}
