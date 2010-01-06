package org.batch.parameter.config;

import java.util.Hashtable;

import org.batch.lang.BatchException;


/** 
 * BatchConfigとJobConfigを保持する.<br>
 * 設定情報に対するクリティカルなアクセスを想定して作成.<br>
 * シングルトンで作成.<br>
 *
 * @author T.Okuyama
 * @license GPL(Lv3)
 */
public class ConfigFolder {

    private static Hashtable table = null;

    // staticイニシャライザ
    static {
        table = new Hashtable(2);
    }

    private ConfigFolder(){}

    // 設定情報をセット
    public static void setConfig(BatchConfig batchConfig, JobConfig jobConfig) {
        table.put("batch", batchConfig);
        table.put("job", jobConfig);
    }


    public static BatchConfig getBatchConfig() {
        return (BatchConfig)table.get("batch");
    }

    public static JobConfig getJobConfig() {
        return (JobConfig)table.get("job");
    }

    /**
     * Helper情報を取り出す.<br>
     * 存在しない場合はExceptionが返る.<br>
     * 
     * @param helperName Helper名
     * @return HelperConfigMap 取得値
     */
    public static HelperConfigMap getHelperConfigMap(String helperName) throws BatchException {
        return (HelperConfigMap)((JobConfig)table.get("job")).getHelperConfig(helperName);
    }

    /**
     * ユーザが自由に設定した値を取り出す.<br>
     * 存在しない場合はnullが返る.<br>
     * 
     * @param key キー値
     * @return String 取得値
     */
    public static String getJobUserParam(String key) {
        return (String)((JobConfig)table.get("job")).getUserParam(key);
    }


}