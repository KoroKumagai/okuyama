import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

//import org.csvdb.lang.CsvDbException;
//import org.csvdb.io.DataDefineConfig;

public class CsvSQLMain {

    public static void main(String[] args) {
        CsvSQLMain me = new CsvSQLMain();
        me.exec(args);
    }

    public void exec(String[] args) {
        try {
            String[] work = args[0].split(";");

            StatementCompileData statementCompileData = new StatementCompileData(work[0]);
            statementCompileData.compile();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private class StatementCompileData {

    // �X�e�[�g�����g�^�C�v�ԍ��iSELECT)
    private static final int ST_TYPE_SELECT = 1;
    // �X�e�[�g�����g�^�C�v�ԍ��iERROR)
    private static final int ST_TYPE_ERR = 2;

    // �X�e�[�g�����g�^�C�v�̔��f�p
    private static final String ST_TYPE_SELECT_STR = "select";

    // �X�e�[�g�����g����p�̃f���~�^
    private static final String ST_TOKEN_DELIM = " ";


    // �����w��́u=�v
    public static final String WHERE_TYPE_EQUAL = "1";

    // �����w��́u<>�v
    public static final String WHERE_TYPE_NOT_EQUAL = "2";

    // �����w��́ulike�v
    public static final String WHERE_TYPE_LIKE = "3";

    // �����w��́u<�v
    public static final String WHERE_TYPE_RIGHT_BIG = "4";

    // �����w��́u>�v
    public static final String WHERE_TYPE_LEFT_BIG = "5";

    // �����w��́uin�v
    public static final String WHERE_TYPE_IN = "6";


    // WHERE��̗��ӂ̌^�u������v
    public static final String WHERE_VALUE_TYPE_STRING = "STRING";

    // WHERE��̗��ӂ̌^�u���l�v
    public static final String WHERE_VALUE_TYPE_NUMBER = "NUMBER";

    // WHERE��̗��ӂ̌^�u�e�[�u���v
    public static final String WHERE_VALUE_TYPE_TABLE = "TABLE";


    // �R���p�C�����̃G���[�L�����i�[
    private boolean compErrFlg;

    // �R���p�C�����̃G���[������
    private String compErrMsg;


    // ���g�����X�e�[�g�����g������
    private String statement;

    // ���g���R���p�C�����Ă���X�e�[�g�����g�̃^�C�v 1=SELECT 2=UPDATE 3=INSERT 4=DELETE -1=�G���[
    public static final int ST_TYPE_NUMBER_SELECT = 1;
    public static final int ST_TYPE_NUMBER_UPDATE = 2;
    public static final int ST_TYPE_NUMBER_INSERT = 3;
    public static final int ST_TYPE_NUMBER_DELETE = 4;
    public static final int ST_TYPE_NUMBER_ERROR = -1;
    private int stTypeNumber = ST_TYPE_NUMBER_ERROR;


    /**
     * SELECT��̃J�������X�g.<br>
     * SELECT��̃J�����̏ڍ׏����i�[.<br>
     * ���e��HashMap<br>
     * ArrayList{(0) HashMap{"COLUMN_NAME", "COL_A"},<br>
     *           (1) HashMap{"COLUMN_NAME", "COL_B"}<br>
     *          }<br>
     */
    private ArrayList selectColsDtList;


    /**
     * FROM��̃e�[�u�����X�g.<br>
     * FROM��̃e�[�u���̏ڍ׏����i�[.<br>
     * ���e��HashMap<br>
     * ArrayList{(0) HashMap{"TABLE_NAME", "TABLE_A"},<br>
     *           (1) HashMap{"TABLE_NAME", "TABLE_B"}<br>
     *          }<br>
     *
     */
    private ArrayList fromTablesDtList;

    /**
     * WHERE��̏ڍ׃��X�g(���e��OR��o�Ă���܂ł���̏W���Ƃ��āA<br>
     * ArrayList�ɂ��Ċi�[.<br>
     * ������ArrayList�̒��g��HashMap<br>
     * ArrayList{(0) ArrayList{HashMap{["LEFT", "COLUMN_A"],<br>
     *                                 ["LEFT_TYPE", "COLUMN"], <br>
     *                                 ["RIGHT", "'A'"], <br>
     *                                 ["RIGHT_TYPE", "STRING"], <br>
     *                                 ["TYPE", "="]<br>
     *                                },<br>
     *                        },<br>
     *                        {HashMap{["LEFT", "COLUMN_B"],<br>
     *                                 ["LEFT_TYPE", "COLUMN"], <br>
     *                                 ["RIGHT", "1"], <br>
     *                                 ["RIGHT_TYPE", "NUMBER"], <br>
     *                                 ["TYPE", "<>"]<br>
     *                                },<br>
     *                        },<br>
     *           (1) ArrayList{HashMap{["LEFT", "COLUMN_A"],<br>
     *                                 ["LEFT_TYPE", "COLUMN"], <br>
     *                                 ["RIGHT", "'B'"], <br>
     *                                 ["RIGHT_TYPE", "STRING"], <br>
     *                                 ["TYPE", "="]<br>
     *                                },<br>
     *                        },<br>
     *                        {HashMap{["LEFT", "COLUMN_B"],<br>
     *                                 ["LEFT_TYPE", "COLUMN"], <br>
     *                                 ["RIGHT", "2"], <br>
     *                                 ["RIGHT_TYPE", "NUMBER"], <br>
     *                                 ["TYPE", "<>"]<br>
     *                                },<br>
     *                        }<br>
     */ 
    private ArrayList whereDtList;


    /**
     * �I�v�V�����R�}���h���i�[.<br>
     * HashMap{["OPTION_CMD", "OUT"],<br>
     *         ["OPTION_STR", "STR"]<br>
     *        }<br>
     *
     * OPTION_CMD�ɃI�v�V�����w�蕶������i�[<br>
     * ���݃T�|�[�g���Ă���͉̂��L<br>
     * "OUT" => ���ʂ��t�@�C���ɏo��<br>
     *
     * OPTION_STR�ɃI�v�V����������������i�[<br>
     * ���݃T�|�[�g���Ă���͉̂��L<br>
     * "OUT"�ɑ΂������������ => /etc/CSV_RET.csv<br>
     *
     */
    private HashMap optionCmdMap;


    /**
     * �R�X�g���N�^
     * @param statement �₢���킹������
     */
    public StatementCompileData(String statement) {
        this.statement = statement;
        this.compErrFlg = false;
        this.compErrMsg = null;
        this.selectColsDtList = new ArrayList();
        this.fromTablesDtList = new ArrayList();
        this.whereDtList = new ArrayList();
        this.optionCmdMap = new HashMap();
    }

    /**
     * Statement���R���p�C������.<br>
     *
     */
    public void compile() throws Exception {
        String targetStr = null;
        try {
            switch (this.checkStatementType()) {
                case ST_TYPE_SELECT:
                    // SELECT�X�e�[�g�����g

                    // SELECT�啪��
                    targetStr = this.parseSelectColumn();

                    if (this.compErrFlg) break;

                    // FROM�啪��
                    targetStr = this.parseFrom(targetStr);

                    if (this.compErrFlg) break;

                    // WHERE�啪��
                    targetStr = this.parseWhere(targetStr);
                    if (this.compErrFlg) break;

                    // ���`�F�b�N
                    //this.checkAll();
                    //if (this.compErrFlg) break;

                    this.stTypeNumber = ST_TYPE_NUMBER_SELECT;
                    break;
                case ST_TYPE_ERR:
                    // ����ȊO ���̂Ƃ���Ȃ�
                    this.compErrFlg = true;
                    this.compErrMsg = "�Ή����Ă��Ȃ��X�e�[�g�����g�ł� =[" + this.statement + "]";
                    this.stTypeNumber = ST_TYPE_NUMBER_ERROR;
                break;
            }
            
        
        } catch (Exception e) {
            throw e;
        }
        System.out.println("selectColsDtList = [" + selectColsDtList + "]");
        System.out.println("fromTablesDtList = [" + fromTablesDtList + "]");
        System.out.println("whereDtList = [" + whereDtList + "]");
    }

    public ArrayList getSelectColsDtList() {
        return this.selectColsDtList;
    }

    public ArrayList getFromTablesDtList() {
        return this.fromTablesDtList;
    }

    public ArrayList getWhereDtList() {
        return this.whereDtList;
    }

    /**
     * �X�e�[�g�����g�̃^�C�v�𒲂ׂ�.<br>
     * 1=SELECT<br>
     * 2=����ȊO ���܂̂Ƃ���Ȃ�<br>
     * @return int 1=SELECT 2=����ȊO ���܂̂Ƃ���Ȃ�
     */
    public int checkStatementType() throws Exception {

        int ret = ST_TYPE_ERR;
        String firstToken = null;

        // �g��������
        String workSt = this.statement.trim();

        // �g�[�N��������
        StringTokenizer token = new StringTokenizer(workSt, ST_TOKEN_DELIM);

        try {
            if (token.hasMoreTokens()) {
                firstToken = token.nextToken(ST_TOKEN_DELIM);
            }

            if (firstToken != null && firstToken.trim().toLowerCase().equals(ST_TYPE_SELECT_STR)) {
                ret = ST_TYPE_SELECT;
            }
        } catch (Exception e) {
            throw e;
        }
        return ret;
    }


    /**
     * SELECT��𕪉�����.<br>
     * ","�ŕ������Ĉ��؂�ɂ���.<br>
     * �A���A"'"��������"("���o�Ă����ꍇ�͎���"'"��������")"���o�Ă���܂ł���̋�؂�ɂ���.<br>
     * �{���\�b�g�͉�͌�̎c�蕶�����Ԃ�.<br>
     *
     */
    private String parseSelectColumn() {
        HashMap colsDtMap = null;
        ArrayList addStrList = new ArrayList(50);
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        int bracketsFlgCount = 0;
        String workStr = "";

        String targetSt = null;
        char[] selectChars = null;
        String[] fromSplitList = null;

        // �uselect�v���폜
        targetSt = this.deleteSelectStr(this.statement);

        if (targetSt.trim().length() == 0) {
            // SELECT�傪���݂��Ȃ�
            this.compErrFlg = true;
            this.compErrMsg = "SELECT�傪���݂��܂���:" + this.statement;
            return null;
        } 

        // �ufrom�v�ŕ���
        fromSplitList = this.splitSqlFrom(targetSt);

        // FROM��ȑO�i�[
        targetSt = fromSplitList[0];

        // SELECT��̍Ōオ","�ŏI����Ă���ꍇ�p�Ɉꕶ���v���X����
        // ���[�v���ɍŌ�܂œ�������
        targetSt = targetSt + " ";
        selectChars = targetSt.toCharArray();

        // �u , �v���o�Ă���܂ňړ�
        for(int charIndex = 0;charIndex < selectChars.length;charIndex++){
            // ���݁u'�v�̒������f
            if(singleQuotationFlg == false && bracketsFlg == false){
                if(charIndex + 1 < selectChars.length){
                    if(selectChars[charIndex] == ','){
                        // �u,�v�Ȃ̂ł����܂ł̕�������i�[
                        addStrList.add(workStr);
                        workStr = "";
                        charIndex = charIndex + 1;
                    }
                }
            }

            if(selectChars[charIndex] == '\''){
                if(singleQuotationFlg == false){
                    singleQuotationFlg = true;
                }else{
                    singleQuotationFlg = false;
                }
            }

            if(singleQuotationFlg == false){
                if(selectChars[charIndex] == '('){
                    bracketsFlgCount++;
                    bracketsFlg = true;
                }

                if(selectChars[charIndex] == ')'){
                    bracketsFlgCount--;
                    if(bracketsFlgCount == 0)  bracketsFlg = false;
                }
            }

            workStr = workStr + String.valueOf(selectChars[charIndex]);
        }

        addStrList.add(workStr);

        for(int i = 0;i < addStrList.size();i++){
            colsDtMap = new HashMap();

            // ���̂Ƃ���J�������̂݊i�[
            // �K�v�Ȃ炱���ōēx��͂��Ă���������
            colsDtMap.put("COLUMN_NAME", ((String)addStrList.get(i)).trim());

            if (((String)addStrList.get(i)).trim().equals("")) {
                // SELECT��ɕs��������
                this.compErrFlg = true;
                this.compErrMsg = "SELECT�傪�s���ł��B:" + this.statement;
                return null;
            }

            selectColsDtList.add(colsDtMap);
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
                this.compErrFlg = true;
                this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
        }

        return fromSplitList[1];
    }


    /**
     * FROM����e�[�u���P�ʂŕ�������.<br>
     * ","�ŕ������Ĉ��؂�ɂ���.<br>
     * �A���A"'"��������"("���o�Ă����ꍇ�͎���"'"��������")"���o�Ă���܂ł���̋�؂�ɂ���.<br>
     * �{���\�b�g�͉�͌�̎c�蕶�����Ԃ�.<br>
     * 
     */
    private String parseFrom(String targetSt) {
        HashMap tableDtMap = null;
        ArrayList addStrList = new ArrayList(50);
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        int bracketsFlgCount = 0;
        String workStr = "";

        char[] fromChars = null;
        String[] whereSplitList = null;

        // FROM�呶�݊m�F
        if (targetSt == null || targetSt.trim().length() == 0) {
            // FROM�傪���݂��Ȃ�
            this.compErrFlg = true;
            this.compErrMsg = "FROM�傪���݂��܂���:" + this.statement;
            return null;
        } 

        // �uwhere�v�ŕ���
        whereSplitList = this.splitSqlWhere(targetSt);

        // FROM��ȑO�i�[
        targetSt = whereSplitList[0];

        // FROM��̍Ōオ","�ŏI����Ă���ꍇ�p�Ɉꕶ���v���X����
        // ���[�v���ɍŌ�܂œ�������
        targetSt = targetSt + " ";
        fromChars = targetSt.toCharArray();

        // �u , �v���o�Ă���܂ňړ�
        for(int charIndex = 0;charIndex < fromChars.length;charIndex++){
            // ���݁u'�v�̒������f
            if(singleQuotationFlg == false && bracketsFlg == false){
                if(charIndex + 1 < fromChars.length){
                    if(fromChars[charIndex] == ','){
                        // �u,�v�Ȃ̂ł����܂ł̕�������i�[
                        addStrList.add(workStr);
                        workStr = "";
                        charIndex = charIndex + 1;
                    }
                }
            }

            if(fromChars[charIndex] == '\''){
                if(singleQuotationFlg == false){
                    singleQuotationFlg = true;
                }else{
                    singleQuotationFlg = false;
                }
            }

            if(singleQuotationFlg == false){
                if(fromChars[charIndex] == '('){
                    bracketsFlgCount++;
                    bracketsFlg = true;
                }

                if(fromChars[charIndex] == ')'){
                    bracketsFlgCount--;
                    if(bracketsFlgCount == 0)  bracketsFlg = false;
                }
            }

            workStr = workStr + String.valueOf(fromChars[charIndex]);
        }

        addStrList.add(workStr);

        for(int i = 0;i < addStrList.size();i++){
            tableDtMap = new HashMap();

            // ���̂Ƃ���e�[�u�����̂݊i�[
            // �K�v�Ȃ炱���ōēx��͂��Ă���������
            tableDtMap.put("TABLE_NAME", ((String)addStrList.get(i)).trim());

            if (((String)addStrList.get(i)).trim().equals("")) {
                // FROM��ɕs��������
                this.compErrFlg = true;
                this.compErrMsg = "FROM�傪�s���ł��B:" + this.statement;
                return null;
            }

            this.fromTablesDtList.add(tableDtMap);
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
            this.compErrFlg = true;
            this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
            return null;
        }

        return whereSplitList[1];
    }


    /**
     * where��𕪐�
     *
     */
    public String parseWhere(String targetSt) {
        HashMap whereDtMap = null;
        ArrayList whereAndList = null;

        String[] orSplitList = null;
        String[] andSplitList = null;
        String[] groupBySplitList = null;

        // WHERE��ȍ~���݊m�F
        if (targetSt == null || targetSt.trim().length() == 0) {
           // WHERE��ȍ~�����݂��Ȃ�
           return null;
        } 

        // �uorder by�v�ŕ��� 
        // TODO:��Ŏ���
        //groupBySplitList = this.splitSqlOrderBy(targetSt);
        groupBySplitList = new String[2];

        // FROM��ȑO�i�[
        // TODO:��Ŏ���
        //targetSt = groupBySplitList[0];
        // TODO:��
        targetSt = targetSt;

        // OR��ŕ���
        orSplitList = this.splitSqlOr(targetSt);

        // OR��P�ʂŕ���
        for (int orIndex = 0; orIndex < orSplitList.length; orIndex++) {

            // ���AND��̂܂Ƃ܂胊�X�g
            whereAndList = new ArrayList();

            // AND��ŕ���
            andSplitList = this.splitSqlAnd(orSplitList[orIndex]);

            // AND�P�ʂŕ���
            for (int andIndex = 0; andIndex < andSplitList.length; andIndex++) {
                // ��̏�����𕪐�
                whereDtMap = this.parseWhereOneDt(andSplitList[andIndex]);
                // null�������ꍇ���͎��s
                if (whereDtMap == null) break;
                whereAndList.add(whereDtMap);
            }
            this.whereDtList.add(whereAndList);
        }

        return groupBySplitList[1];
    }


    /**
     * SQL����Where���̏�������͂���
     * RIGHT = �E�ӂ̍��ڂ��i�[
     * RIGHT_TYPE = �E�ӂ̍��ڂ̃^�C�v(STRING, NUMBER, COLUMN)
     * LEFT = ���ӂ̍��ڂ��i�[
     * LEFT_TYPE = ���ӂ̍��ڂ̃^�C�v(STRING, NUMBER, COLUMN)
     * TYPE = ����
     *
     * ��) �utable1.Acolumn = '2'�v
     *                               ��
     *      [RIGHT='2', RIGHT_TYPE='STRING', LEFT='table1.Acolumn', LEFT_TYPE='COLUMN', TYPE='='}
     *
     * @param oneStr ������
     * @return HashMap ��͌�̃}�b�v
     */
    private HashMap parseWhereOneDt(String str) {
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        int bracketsFlgCount = 0;
        String[] retList;
        str = str.trim();
        char[] lineChars = str.toCharArray();
        String type = "";
        StringBuffer workStrBuf = new StringBuffer();
        StringBuffer rightStrBuf = null;
        String leftStr = null;

        HashMap retMap = new HashMap();

        // �u = �v���o�Ă���܂ňړ�
        if(str.indexOf("=") != -1){
            for(int charIndex = 0;charIndex < lineChars.length;charIndex++){
                // ���݁u'�v�̒������f
                if(singleQuotationFlg == false && bracketsFlg == false){
                    if(lineChars[charIndex] == '='){
                        // �u=�v�������o�Ă����̂ł����܂ł̕������A��
                        leftStr = workStrBuf.toString();
                        rightStrBuf = new StringBuffer();
                        for(int lastIndex = charIndex + 1;lastIndex < lineChars.length;lastIndex++){
                            rightStrBuf.append(lineChars[lastIndex]);
                        }
                        type = WHERE_TYPE_EQUAL;
                        break;
                    }
                }

                if(lineChars[charIndex] == '\''){
                    if(singleQuotationFlg == false){
                        singleQuotationFlg = true;
                    }else{
                        singleQuotationFlg = false;
                    }
                }

                if(singleQuotationFlg == false){
                    if(lineChars[charIndex] == '('){
                        bracketsFlgCount++;
                        bracketsFlg = true;
                    }

                    if(lineChars[charIndex] == ')'){
                        bracketsFlgCount--;
                        if(bracketsFlgCount == 0)  bracketsFlg = false;
                    }
                }

                workStrBuf.append(String.valueOf(lineChars[charIndex]));
            }
        }

        // �u <> �v���o�Ă���܂ňړ�
        if(type.equals("")){
            workStrBuf = new StringBuffer();
            if(str.indexOf("<>") != -1){
                for(int charIndex = 0;charIndex < lineChars.length;charIndex++){
                // ���݁u'�v�̒������f
                if(singleQuotationFlg == false && bracketsFlg == false){

                    if(lineChars[charIndex] == '<' && 
                        lineChars[charIndex + 1] == '>'){
                        // �u<>�v�������o�Ă����̂ł����܂ł̕������A��
                        leftStr = workStrBuf.toString();
                        rightStrBuf = new StringBuffer();
                        for(int lastIndex = charIndex + 2;lastIndex < lineChars.length;lastIndex++){
                            rightStrBuf.append(lineChars[lastIndex]);

                        }
                        type = WHERE_TYPE_NOT_EQUAL;
                        break;
                    }
                }

                if(lineChars[charIndex] == '\''){
                    if(singleQuotationFlg == false){
                        singleQuotationFlg = true;
                    }else{
                        singleQuotationFlg = false;
                    }
                }

                if(singleQuotationFlg == false){
                    if(lineChars[charIndex] == '('){
                        bracketsFlgCount++;
                        bracketsFlg = true;
                    }

                    if(lineChars[charIndex] == ')'){
                        bracketsFlgCount--;
                        if(bracketsFlgCount == 0)  bracketsFlg = false;
                    }
                }

                workStrBuf.append(lineChars[charIndex]);
                }
            }
        }


        // �u > �v���o�Ă���܂ňړ�
        if(type.equals("")){
            workStrBuf = new StringBuffer();
            if(str.indexOf(">") != -1){
                for(int charIndex = 0;charIndex < lineChars.length;charIndex++){
                // ���݁u'�v�̒������f
                    if(singleQuotationFlg == false && bracketsFlg == false){
                        if(lineChars[charIndex] == '>'){
                            // �u>�v�������o�Ă����̂ł����܂ł̕������A��
                            leftStr = workStrBuf.toString();
                            rightStrBuf = new StringBuffer();
                            for(int lastIndex = charIndex + 1;lastIndex < lineChars.length;lastIndex++){
                                rightStrBuf.append(lineChars[lastIndex]);
                            }
                            type = WHERE_TYPE_LEFT_BIG;
                            break;
                        }
                    }

                    if(lineChars[charIndex] == '\''){
                        if(singleQuotationFlg == false){
                            singleQuotationFlg = true;
                        }else{
                            singleQuotationFlg = false;
                        }
                    }

                    if(singleQuotationFlg == false){
                        if(lineChars[charIndex] == '('){
                            bracketsFlgCount++;
                            bracketsFlg = true;
                        }

                        if(lineChars[charIndex] == ')'){
                            bracketsFlgCount--;
                            if(bracketsFlgCount == 0)  bracketsFlg = false;
                        }
                    }

                    workStrBuf.append(lineChars[charIndex]);
                }
            }
        }


        // �u < �v���o�Ă���܂ňړ�
        if(type.equals("")){
            workStrBuf = new StringBuffer();
            if(str.indexOf("<") != -1){
                for(int charIndex = 0;charIndex < lineChars.length;charIndex++){
                    // ���݁u'�v�̒������f
                    if(singleQuotationFlg == false && bracketsFlg == false){
                        if(lineChars[charIndex] == '<'){
                            // �u<�v�������o�Ă����̂ł����܂ł̕������A��
                            leftStr = workStrBuf.toString();
                            rightStrBuf = new StringBuffer();
                            for(int lastIndex = charIndex + 1;lastIndex < lineChars.length;lastIndex++){
                                rightStrBuf.append(lineChars[lastIndex]);
                            }
                            type = WHERE_TYPE_RIGHT_BIG;
                            break;
                        }
                    }

                    if(lineChars[charIndex] == '\''){
                        if(singleQuotationFlg == false){
                            singleQuotationFlg = true;
                        }else{
                            singleQuotationFlg = false;
                        }
                    }

                    if(singleQuotationFlg == false){
                        if(lineChars[charIndex] == '('){
                            bracketsFlgCount++;
                            bracketsFlg = true;
                        }

                        if(lineChars[charIndex] == ')'){
                            bracketsFlgCount--;
                            if(bracketsFlgCount == 0)  bracketsFlg = false;
                        }
                    }

                    workStrBuf.append(lineChars[charIndex]);
                }
            }
        }


        // �u like �v���o�Ă���܂ňړ�
        if(type.equals("")){
            workStrBuf = new StringBuffer();
            if(str.toLowerCase().indexOf("like") != -1){
                for(int charIndex = 0;charIndex < lineChars.length;charIndex++){
                    // ���݁u'�v�̒������f
                    if(singleQuotationFlg == false && bracketsFlg == false){

                        if(lineChars[charIndex] == ' ' && 
                            (lineChars[charIndex + 1] == 'l' || lineChars[charIndex + 1] == 'L') && 
                            (lineChars[charIndex + 2] == 'i' || lineChars[charIndex + 2] == 'I') && 
                            (lineChars[charIndex + 3] == 'k' || lineChars[charIndex + 3] == 'K') && 
                            (lineChars[charIndex + 4] == 'e' || lineChars[charIndex + 4] == 'E') && 
                            (lineChars[charIndex + 5] == ' ' || lineChars[charIndex + 5] == '\'')){
                            // �ulike�v�������o�Ă����̂ł����܂ł̕������A��
                            leftStr = workStrBuf.toString();
                            rightStrBuf = new StringBuffer();

                            for(int lastIndex = charIndex + 6;lastIndex < lineChars.length;lastIndex++){
                                rightStrBuf.append(lineChars[lastIndex]);
                            }
                            type = WHERE_TYPE_LIKE;
                            break;
                        }
                    }

                    if(lineChars[charIndex] == '\''){
                        if(singleQuotationFlg == false){
                            singleQuotationFlg = true;
                        }else{
                            singleQuotationFlg = false;
                        }
                    }

                    if(singleQuotationFlg == false){
                        if(lineChars[charIndex] == '('){
                            bracketsFlgCount++;
                            bracketsFlg = true;
                        }

                        if(lineChars[charIndex] == ')'){
                            bracketsFlgCount--;
                            if(bracketsFlgCount == 0)  bracketsFlg = false;
                        }
                    }

                    workStrBuf.append(lineChars[charIndex]);
                }
            }
        }

        // �u in �v���o�Ă���܂ňړ�
        if(type.equals("")){
            workStrBuf = new StringBuffer();
            if(str.toLowerCase().indexOf("in") != -1){
                for(int charIndex = 0;charIndex < lineChars.length;charIndex++){
                // ���݁u'�v�̒������f
                if(singleQuotationFlg == false && bracketsFlg == false){

                    if(lineChars[charIndex] == ' ' && 
                       (lineChars[charIndex + 1] == 'i' || lineChars[charIndex + 1] == 'I') && 
                       (lineChars[charIndex + 2] == 'n' || lineChars[charIndex + 2] == 'N')){
                        // �uin�v�������o�Ă����̂ł����܂ł̕������A��
                        leftStr = workStrBuf.toString();
                        rightStrBuf = new StringBuffer();

                        for(int lastIndex = charIndex + 3;lastIndex < lineChars.length;lastIndex++){
                            rightStrBuf.append(lineChars[lastIndex]);
                        }

                        rightStrBuf = new StringBuffer(rightStrBuf.toString().trim());

                        lineChars = rightStrBuf.toString().toCharArray();

                        // in�傪�������w�肳��Ă��邩���`�F�b�N
                        if(!(lineChars[0] == '(' && lineChars[lineChars.length - 1] == ')')) break;

                        type = WHERE_TYPE_IN;
                        break;
                    }
                }

                if(lineChars[charIndex] == '\''){
                    if(singleQuotationFlg == false){
                        singleQuotationFlg = true;
                    }else{
                        singleQuotationFlg = false;
                    }
                }

                if(singleQuotationFlg == false){
                    if(lineChars[charIndex] == '('){
                        bracketsFlgCount++;
                        bracketsFlg = true;
                    }

                    if(lineChars[charIndex] == ')'){
                        bracketsFlgCount--;
                        if(bracketsFlgCount == 0)  bracketsFlg = false;
                    }
                }

                workStrBuf.append(lineChars[charIndex]);
                }
            }
        }

        // ��͂Ɏ��s�����ꍇ�̓G���[���s
        if(type.equals("") || 
           (leftStr == null || leftStr.trim().equals("")) || 
           (rightStrBuf == null || rightStrBuf.toString().trim().equals(""))){
            this.compErrFlg = true;
            this.compErrMsg = "WHERE�傪�s���ł�:" + str;
            return null;
        }

        // �쐬���������i�[
        retMap.put("TYPE",type);

        retMap.put("LEFT",leftStr.trim());
        // ���ӂ̌^�𒲂ׂ�
        retMap.put("LEFT_TYPE", this.checkStrType(leftStr.trim()));

        retMap.put("RIGHT", rightStrBuf.toString().trim());
        // where�傪"in"�ł͖����ꍇ�E�ӂ̌^�𒲂ׂ�
        if (!type.equals(WHERE_TYPE_IN)) {
            retMap.put("RIGHT_TYPE", this.checkStrType(rightStrBuf.toString().trim()));
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
            this.compErrFlg = true;
            this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
        }

        return retMap;
    }


    /**
     * ���`�F�b�N���W�b�N
     *
     */
    /*private void checkAll() {
        HashMap selectDt = null;
        HashMap fromDt = null;
        ArrayList whereDtList = null;
        HashMap whereDt = null;

        // SELECT��`�F�b�N
        // �J�������`�F�b�N
        if (this.selectColsDtList.size() < 1) {
            this.compErrFlg = true;
            this.compErrMsg = "SELECT��ɂ͍Œ�1�̓J�����w�肪�K�v�ł�:" + this.statement;
            return;
        }
        // �J�������݃`�F�b�N
        for (int i = 0; i < this.selectColsDtList.size(); i++) {
            selectDt = (HashMap)this.selectColsDtList.get(i);
            if (!DataDefineConfig.isExistColumn((String)selectDt.get("COLUMN_NAME"))) {
                this.compErrFlg = true;
                this.compErrMsg = "SELECT��ɑ��݂��Ȃ��J�������w�肳��܂���:" + (String)selectDt.get("COLUMN_NAME");
                return;
            }
        }


        // FROM��`�F�b�N
        // �e�[�u�����`�F�b�N
        if (this.fromTablesDtList.size() != 1) {
            this.compErrFlg = true;
            this.compErrMsg = "FROM��ɂ�1�e�[�u���w�肵���o���܂���:" + this.statement;
            return;
        }
        // �e�[�u�����݃`�F�b�N
        // TODO:��荇�����e�[�u������"csv"�Œ�ɂ���
        for (int i = 0; i < this.fromTablesDtList.size(); i++) {
            fromDt = (HashMap)this.fromTablesDtList.get(i);
            if (!((String)fromDt.get("TABLE_NAME")).toLowerCase().equals("csv")) {
                this.compErrFlg = true;
                this.compErrMsg = "FROM��ɂ́ucsv�v�Ƃ����e�[�u�����̂ݎw��ł��܂�:" + (String)fromDt.get("TABLE_NAME");
                return;
            }
        }


    }
*/

    /**
     * SQL����FROM��ŕ������A������̔z��ɂ��ĕԂ�
     * ��)�uselect * from Atable,Btable�v
     *                    ��
     *    String[0] = select * , String[1] = Atable,Btable
     * @param String sql SQL������
     * @return String[] ������̔z��
     */
     private String[] splitSqlFrom(String targetStr) {

        String workSt = targetStr.trim();
        boolean checkFromStr = false;
        char[] sqlChars = workSt.toCharArray();
        String[] retList = new String[2];
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        int bracketsFlgCount = 0;

        String workStr = "";

        // �u from �v���o�Ă���܂ňړ�
        for(int charIndex = 0;charIndex < sqlChars.length;charIndex++){
            // ���݁u'�v�̒������f
            if(singleQuotationFlg == false && bracketsFlg == false){
                if(charIndex + 5 < sqlChars.length){
                    if(sqlChars[charIndex] == ' ' && 
                         (sqlChars[charIndex + 1] == 'f' || sqlChars[charIndex + 1] == 'F') &&
                         (sqlChars[charIndex + 2] == 'r' || sqlChars[charIndex + 2] == 'R')&&
                         (sqlChars[charIndex + 3] == 'o' || sqlChars[charIndex + 3] == 'O')&& 
                         (sqlChars[charIndex + 4] == 'm' || sqlChars[charIndex + 4] == 'M')&& 
                         sqlChars[charIndex + 5] == ' ' ){
                        // �ufrom�v��Ȃ̂ł����܂ł̕�������i�[
                        retList[0] = workStr;
                        workStr = "";
                        charIndex = charIndex + 5;
                        checkFromStr = true;
                    }
                }
            }

            if(sqlChars[charIndex] == '\''){
                if(singleQuotationFlg == false){
                    singleQuotationFlg = true;
                }else{
                    singleQuotationFlg = false;
                }
            }

            if(singleQuotationFlg == false){
                if(sqlChars[charIndex] == '('){
                    bracketsFlgCount++;
                    bracketsFlg = true;
                }

                if(sqlChars[charIndex] == ')'){
                    bracketsFlgCount--;
                    if(bracketsFlgCount == 0)  bracketsFlg = false;
                }
            }

            workStr = workStr + String.valueOf(sqlChars[charIndex]);
        }

        if(checkFromStr == false) {
            retList[0] = workStr;
            retList[1] = null;
        } else {
            retList[1] = workStr;
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
                this.compErrFlg = true;
                this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
        }

        return retList;
    }


    /**
     * SQL����where��ŕ������A������̔z��ɂ��ĕԂ�
     * ��)�uselect * from Atable,Btable where Atable.A = 'a'�v
     *                    ��
     *    String[0] = select * from Atable,Btable, String[1] = Atable.A = 'a'
     * @param String sql SQL������
     * @return String[] ������̔z��
     */
     private String[] splitSqlWhere(String targetStr) {
        targetStr = targetStr.trim();
        char[] sqlChars = targetStr.toCharArray();
        String[] workRet = new String[2];
        String[] retList = null;
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        boolean whereSplitedFlg = false;
        int bracketsFlgCount = 0;

        String workStr = "";

        // �u where �v���o�Ă���܂ňړ�
        for(int charIndex = 0;charIndex < sqlChars.length;charIndex++){
            // ���݁u'�v�̒������f
            if(singleQuotationFlg == false && bracketsFlg == false && whereSplitedFlg == false){
                if(charIndex + 6 < sqlChars.length){
                    if(sqlChars[charIndex] == ' ' && 
                      (sqlChars[charIndex + 1] == 'w' || sqlChars[charIndex + 1] == 'W') &&
                      (sqlChars[charIndex + 2] == 'h' || sqlChars[charIndex + 2] == 'H') &&
                      (sqlChars[charIndex + 3] == 'e' || sqlChars[charIndex + 3] == 'E') && 
                      (sqlChars[charIndex + 4] == 'r' || sqlChars[charIndex + 4] == 'R') && 
                      (sqlChars[charIndex + 5] == 'e' || sqlChars[charIndex + 5] == 'E') &&
                      sqlChars[charIndex + 6] == ' ' ){
                        // �uwhere�v��Ȃ̂ł����܂ł̕�������i�[
                        workRet[0] = workStr;
                        workStr = "";
                        charIndex = charIndex + 6;
                        whereSplitedFlg = true;
                    }
                }
            }

            if(sqlChars[charIndex] == '\''){
                if(singleQuotationFlg == false){
                    singleQuotationFlg = true;
                }else{
                    singleQuotationFlg = false;
                }
            }

            if(singleQuotationFlg == false){
                if(sqlChars[charIndex] == '('){
                    bracketsFlgCount++;
                    bracketsFlg = true;
                }

                if(sqlChars[charIndex] == ')'){
                    bracketsFlgCount--;
                    if(bracketsFlgCount == 0)  bracketsFlg = false;
                }
            }

            workStr = workStr + String.valueOf(sqlChars[charIndex]);
        }

        // "where"��̑��݂��m�F
        if(workRet[0] == null){

            // "where��"�Ȃ�
            retList = new String[2];
            retList[0] = workStr;
            retList[1] = null;
        }else{
            retList = new String[2];
            retList[0] = workRet[0];
            retList[1] = workStr;
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
                this.compErrFlg = true;
                this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
        }

        return retList;
    }


    /**
     * SQL����OR�A���w�ŕ������A������̔z��ɂ��ĕԂ�
     * ��)�ut1.ACODE = 'B' or t2.BCODE = 'C' and t2.CCODE = 'D' or t1.NAME = 'AA'�v
     *                    ��
     *    String[0] = t1.ACODE = 'B' , String[1] = t2.BCODE = 'C' and t2.CCODE = 'D' , String[2] = t1.NAME = 'AA'
     * @param String sql SQL������
     * @return String[] ������̔z��
     */
     public String[] splitSqlOr(String sql){

        ArrayList addStrList = new ArrayList(10);
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        int bracketsFlgCount = 0;

        String workStr = "";
        String[] retList;
        sql = sql.trim();
        char[] whereChars = sql.toCharArray();

        // �u or �v���o�Ă���܂ňړ�
        for(int charIndex = 0;charIndex < whereChars.length;charIndex++){
            // ���݁u'�v�̒������f
            if(singleQuotationFlg == false && bracketsFlg == false){
                if(charIndex + 3 < whereChars.length){
    
                    if(whereChars[charIndex] == ' ' && 
                       (whereChars[charIndex + 1] == 'o' || whereChars[charIndex + 1] == 'O') &&
                       (whereChars[charIndex + 2] == 'r' || whereChars[charIndex + 2] == 'R') &&
                       whereChars[charIndex + 3] == ' ' ){
                        // �uand�v��Ȃ̂ł����܂ł̕�������i�[
                        addStrList.add(workStr);
                        workStr = "";
                        charIndex = charIndex + 3;
                    }
                }
            }
  
            if(whereChars[charIndex] == '\''){
                if(singleQuotationFlg == false){
                    singleQuotationFlg = true;
                }else{
                    singleQuotationFlg = false;
                }
            }

            if(singleQuotationFlg == false){
                if(whereChars[charIndex] == '('){
                    bracketsFlgCount++;
                    bracketsFlg = true;
                }

                if(whereChars[charIndex] == ')'){
                    bracketsFlgCount--;
                    if(bracketsFlgCount == 0)  bracketsFlg = false;
                }
            }

            workStr = workStr + String.valueOf(whereChars[charIndex]);
        }

        addStrList.add(workStr);
        retList = new String[addStrList.size()];
        for(int i = 0;i < addStrList.size();i++){
            retList[i] = (String)addStrList.get(i);
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
                this.compErrFlg = true;
                this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
        }

        return retList;
    }


    /**
     * SQL����AND�A���w�ŕ������A������̔z��ɂ��ĕԂ�.<br>
     * OR�A���w�͑��݂��Ȃ����̂Ƃ��ď�������
     * ��)�ut1.ACODE = 'B' AND t2.BCODE = 'C' and t2.CCODE = 'D' AND t1.NAME = 'AA'�v
     *                    ��
     *    String[0] = t1.ACODE = 'B' , String[1] = t2.BCODE = 'C' and t2.CCODE = 'D' , String[2] = t1.NAME = 'AA'
     * @param String sql SQL������
     * @return String[] ������̔z��
     */
     public String[] splitSqlAnd(String whereSql){
        ArrayList addStrList = new ArrayList(10);
        boolean singleQuotationFlg = false;
        boolean bracketsFlg = false;
        int bracketsFlgCount = 0;

        String workStr = "";
        String[] retList;
        whereSql = whereSql.trim();
        char[] whereChars = whereSql.toCharArray();

        // �u and �v���o�Ă���܂ňړ�
        for(int charIndex = 0;charIndex < whereChars.length;charIndex++){
            // ���݁u'�v�̒������f
            if(singleQuotationFlg == false && bracketsFlg == false){
                if(charIndex + 4 < whereChars.length){
                    if(whereChars[charIndex] == ' ' && 
                       (whereChars[charIndex + 1] == 'a' || whereChars[charIndex + 1] == 'A') &&
                       (whereChars[charIndex + 2] == 'n' || whereChars[charIndex + 2] == 'N') &&
                       (whereChars[charIndex + 3] == 'd' || whereChars[charIndex + 3] == 'D') && 
                       whereChars[charIndex + 4] == ' ' ){
                        // �uand�v��Ȃ̂ł����܂ł̕�������i�[
                        addStrList.add(workStr);
                        workStr = "";
                        charIndex = charIndex + 4;
                    }
                }
            }

            if(whereChars[charIndex] == '\''){
                if(singleQuotationFlg == false){
                    singleQuotationFlg = true;
                }else{
                    singleQuotationFlg = false;
                }
            }

            if(singleQuotationFlg == false){
                if(whereChars[charIndex] == '('){
                    bracketsFlgCount++;
                    bracketsFlg = true;
                }

                if(whereChars[charIndex] == ')'){
                    bracketsFlgCount--;
                    if(bracketsFlgCount == 0)  bracketsFlg = false;
                }
            }

            workStr = workStr + String.valueOf(whereChars[charIndex]);
        }

        addStrList.add(workStr);
        retList = new String[addStrList.size()];
        for(int i = 0;i < addStrList.size();i++){
            retList[i] = (String)addStrList.get(i);
        }

        // "'"��"("���I�������ɏI����ĂȂ����`�F�b�N
        if (singleQuotationFlg == true || bracketsFlg == true) {
                this.compErrFlg = true;
                this.compErrMsg = "�X�e�[�g�����g���������I�����Ă��܂���B:" + this.statement;
        }

        return retList;
    }


    /**
     * where��̍��ӕ����񂪃e�[�u����\���Ă���̂��A���l��\���Ă���̂��A�������\���Ă���̂���Ԃ�
     * ��) �utableA.column1�v => "TABLE"
     *     �u'AAA'�v => "STRING"
     *     �u100�v => "NUMBER"
     *
     * @param String str ���f�Ώۂ̕�����
     * @return String ���f����
     */
    public String checkStrType(String str){
        String ret = "";
        str = str.trim();
        int strLen = str.length();

        // ������̐擪�Ɩ����𒲂ׂ�
        if(str.indexOf("'") == 0 && (str.lastIndexOf("'") == (strLen-1))){
            // ������w��
            ret = WHERE_VALUE_TYPE_STRING;
        }else{
            // float�^�ɃL���X�g���A�L���X�g�ł��Ȃ������ꍇ�̓e�[�u���w��
            try{
                float f = Float.parseFloat(str);
                ret = WHERE_VALUE_TYPE_NUMBER;

            }catch(Exception e){
                // �������Ȃ�
                ret = WHERE_VALUE_TYPE_TABLE;
            }
        }
        return ret;
    }


    /**
     * SELECT��������폜.<br>
     */
    private String deleteSelectStr(String str) {
        str = str.trim();

        return str.substring(6, str.length());
        
    }


    /**
     * �R���p�C���G���[�̗L����Ԃ�.<br>
     * @return boolean true:�G���[ false:�G���[����
     */
    public boolean isCompileErr() {
        return this.compErrFlg;
    }

    /**
     * �R���p�C���G���[�̃��b�Z�[�W��Ԃ�.<br>
     * @return String ���b�Z�[�W
     */
    public String getCompileErrMsg() {
        return this.compErrMsg;
    }


    /**
     * ���g���R���p�C�������X�e�[�g�����g�̃^�C�v��Ԃ�.<br>
     *
     * @return int ��ޔԍ�
     */
    public int getStType() {
        return this.stTypeNumber;
    }
}
}