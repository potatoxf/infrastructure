package potatoxf.infrastructure;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p/>
 * Create Time:2024-03-22
 *
 * @author potatoxf
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MessagePacker packer = MessagePack.newDefaultPacker(out);
        packer.packMapHeader(3); // 表示有3个键值对
        packer.packString("name").packString("复习");
        packer.packString("age").packInt(18);
        packer.packMapHeader(2);
        packer.packString("语文").packInt(80);
        packer.packString("英语").packInt(70);
        packer.flush();
        packer.close();
        byte[] byteArray = out.toByteArray();
        System.out.println(Arrays.toString(byteArray));
    }
}
