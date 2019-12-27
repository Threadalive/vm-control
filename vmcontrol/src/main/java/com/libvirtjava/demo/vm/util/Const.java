package com.libvirtjava.demo.vm.util;

/**
 * @Description 常量
 * @Author zhenxing.dong
 * @Date 2019/12/19 15:42
 */
public class Const {
    /**
     * 成功
     */
    public static final String SUCCEED = "succeed";

    /**
     * 失败
     */
    public static final String FAIL = "fail";

    /**
     * 消息
     */
    public static String MSG = "msg";

    /**
     * 创建虚拟机使用的xml
     */
    public static String XMLDESC = "<domain type=\"kvm\">\n" +
            "    <name>%s</name>  <!--虚拟机名称-->\n" +
            "    <memory unit=\"MiB\">%d</memory>   <!--最大内存 -->\n" +
            "    <currentMemory unit=\"MiB\">%d</currentMemory>  <!--可用内存-->\n" +
            "    <vcpu>%d</vcpu>   <!--//虚拟cpu个数-->\n" +
            "    <os>\n" +
            "        <type arch=\"x86_64\" machine=\"pc\">hvm</type>\n" +
            "        <boot dev=\"hd\" /> <!-- 硬盘启动 -->\n" +
            "        <boot dev=\"cdrom\" />     <!--//光盘启动-->\n" +
            "    </os>\n" +
            "    <features>\n" +
            "        <acpi />\n" +
            "        <apic />\n" +
            "        <pae />\n" +
            "    </features>\n" +
            "    <clock offset=\"localtime\" />\n" +
            "    <on_poweroff>destroy</on_poweroff>\n" +
            "    <on_reboot>restart</on_reboot>\n" +
            "    <on_crash>destroy</on_crash>\n" +
            "    <devices>\n" +
            "        <emulator>/usr/libexec/qemu-kvm</emulator>\n" +
            "        <disk type=\"file\" device=\"disk\">\n" +
            "            <driver name=\"qemu\" type=\"qcow2\" />\n" +
            "            <source file=\"%s\" />        <!--目的镜像路径-->\n" +
            "            <target dev=\"hda\" bus=\"ide\" />\n" +
            "        </disk>\n" +
            "        <disk type=\"file\" device=\"cdrom\">\n" +
            "            <source file=\"%s\" />        <!--光盘镜像路径 -->\n" +
            "            <target dev=\"hdb\" bus=\"ide\" />\n" +
            "        </disk>\n" +
            "        <interface type=\"bridge\">       <!--虚拟机网络连接方式-->\n" +
            "            <source bridge=\"virbr0\" />      <!--当前主机网桥的名称-->\n" +
            "        </interface>\n" +
            "        <input type=\"mouse\" bus=\"ps2\" />\n" +
            "        <!--vnc方式登录，端口号自动分配，自动加1，可以通过virsh vncdisplay来查询-->\n" +
            "        <graphics type=\"vnc\" port=\"-1\" autoport=\"yes\" listen=\"0.0.0.0\" keymap=\"en-us\" />\n" +
            "    </devices>\n" +
            "</domain>";

    /**
     * 卷的xml
     */
    public static String VOLXML = "<volume>\n" +
            "    <name>%s.img</name>\n" +
            "    <allocation>0</allocation>\n" +
            "    <capacity unit=\"G\">%d</capacity>\n" +
            "    <target>\n" +
            "        <path>/var/lib/libvirt/images/%s.img</path>\n" +
            "        <format type='qcow2'/>\n" +
            "        <permissions>\n" +
            "        <owner>107</owner>\n" +
            "        <group>107</group>\n" +
            "        <mode>0744</mode>\n" +
            "        <label>virt_image_t</label>\n" +
            "        </permissions>\n" +
            "    </target>\n" +
            "</volume>";
}
