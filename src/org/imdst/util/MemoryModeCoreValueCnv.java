package org.imdst.util;


import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

/**
 * �ŏI�ۑ��}�̂�Memory����Converter.<br>
 * Encode�d�l:Key=BASE64�Ńf�R�[�h��A�o�C�g�z��ŕԂ�
 *            Value=�o�C�g�z��ŕԂ�
 *
 * Decode�d�l:Key=BASE64�ŃG���R�[�h��AString�ŕԂ�
 *            Value=String�ŕԂ�
 *
 *
 * @author T.Okuyama
 * @license GPL(Lv3)
 */
public class MemoryModeCoreValueCnv implements ICoreValueConverter {


    /**
     * ������Object��Base64�ŃG���R�[�h���String
     * �ԋp�l��Base64�Ńf�R�[�h���byte�z��
     *
     */
    public Object convertEncodeKey(Object key) {
        return decode(((String)key).getBytes());
    }

    /**
     * ������Object��Base64�ŃG���R�[�h���ꂽ�����ƃ��^���̘A��������
     * �ԋp�l��byte�z��
     *
     */
    public Object convertEncodeValue(Object value) {
        return ((String)value).getBytes();
    }



    /**
     * ������Object��Base64�ŃG���R�[�h�㒼���byte�z��
     * �ԋp�l��Base64�ŃG���R�[�h��̕�����
     */
    public Object convertDecodeKey(Object key) {
        return new String(encode((byte[])key));
    }

    /**
     * ������Object��Base64�ŃG���R�[�h���ꂽ�����ƃ��^���̘A���������byte�z��
     * �ԋp�l��byte�z��𕶎���ɕύX����String
     *
     */
    public Object convertDecodeValue(Object value) {
        return new String((byte[])value);
    }



    private byte[] encode(byte[] datas) {
        return BASE64EncoderStream.encode(datas)
    }

    private byte[] decode(byte[] datas) {
        return BASE64DecoderStream.decode(datas)
    }


}
