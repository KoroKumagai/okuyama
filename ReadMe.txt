a
====== ���UKey-Value�X�g�A �uokuyama�v=====================================================
Java�Ŏ������ꂽ�A�i�����^���UKey-Value�X�g�A�uokuyama�v��
�_�E�����[�h�������肪�Ƃ��������܂��B

���N�����@�͖{�e�L�X�g�́u���@�\�����ƃT���v���̎��s���@�v���������������B
  �����Ɂuokuyama�\���}.gif�v�����Q�Ƃ��������B
  blog:http://d.hatena.ne.jp/okuyamaoo/


�E���C����
========================================================================================================
[New - �@�\���P]
[[�����[�X Ver 0.8.0 - (2010/08/31)]]
  ���U�蕪�����[�h��ConsistentHash��ǉ�
    �f�[�^���U�A���S���Y�����]����Mod�݂̂��������A�V����ConsistentHash��ǉ��B
    �m�[�h�ǉ����̎����f�[�^�ڍs������
    execOkuyamaManager.bat���N����http://localhost:10088/okuyamamgr�ɃA�N�Z�X���A"Add Main DataNode"�ɒǉ�������
    �m�[�h��IP:PORT���L�q��UPDATE�{�^������������Ǝ����I�Ƀf�[�^�ڍs���s����
    ��Sub�f�[�^�m�[�h�AThird�f�[�^�m�[�h���^�p���Ă���ꍇ�͈�x��"Add Sub DataNode"�A"Add Third DataNode"��
      IP:PORT���L�q����UPDATE�{�^�����������Ȃ��ƍX�V�Ɏ��s����
      �܂�AMainDataNode�������₷�Ƃ��͏o���Ȃ��B
    ��MasterNode�̐ݒ�͑S�m�[�hMod��������ConsistentHash�̂ǂ��炩�ɓ��ꂳ��Ă���K�v������B
      �]����Mod�A���S���Y���ŕۑ������f�[�^��ConsistentHash�Ɉڍs�͏o���Ȃ��B
    MasterNode.properties�̈ȉ��̐ݒ荀�ڂŐ���\

    ��DistributionAlgorithm
        �ݒ�l) "mod"=Mod�A���S���Y��
                "consistenthash"=ConsistentHash�A���S���Y��
        �L�q��)
             DistributionAlgorithm=mod


  ��DataNode�̃��v���P�[�V�������2�m�[�h�ɕύX
    �]����KeyMapNodesInfo�ɑ΂���SubKeyMapNodesInfo�����v���P�[�V������ƂȂ�2�m�[�h�Ńf�[�^�����v���P�[�V����
    ���Ă������A�V����ThirdKeyMapNodesInfo��݂����B
    ThirdKeyMapNodesInfo���L�q����ƁA���v���P�[�V�������s���3�m�[�h��1�g��DataNode�Ƃ��ċ@�\����B
    3�m�[�h�S�Ă���~���Ȃ���Ήғ��\�ł���B
    MasterNode.properties�̈ȉ��̐ݒ荀�ڂŐ���\

    ��ThirdKeyMapNodesInfo
        �ݒ�l) "IP:PORT"

        �L�q��)
             ThirdKeyMapNodesInfo=localhost:7553,localhost:7554


  ���f�[�^�擾���̈�ѐ����[�h��ǉ�
    �f�[�^�擾���Ƀ��v���P�[�V������̏�Ԃɍ��킹�Ď擾�f�[�^�̈�ѐ����ӎ������擾���\�B
    ���[�h��3��ނƂȂ�B
    �E���ѐ�:�����_���Ƀ��C���A���v���P�[�V������̂ǂ�������擾����(����Client�ڑ����g�p���Ă���Ԃ�1�m�[�h�ɌŒ肳���)
    �E����ѐ�:�K���Ō�ɕۑ�����郌�v���P�[�V�����m�[�h����擾����
    �E����ѐ�:���C���A���v���P�[�V�����̒l�����؂��A�V�����f�[�^��Ԃ�(�Б����폜����Ă����ꍇ�̓f�[�^�L�肪�Ԃ�)
    MasterNode.properties�̈ȉ��̐ݒ荀�ڂŐ���\

    ��DataConsistencyMode
        �ݒ�l) "0"
                "1"
                "2"
 
        �L�q��)
             DataConsistencyMode=1




========================================================================================================
========================================================================================================
[New - �@�\���P]
[[�����[�X Ver 0.7.0 - (2010/06/27)]]
  ��MasterNode�𕡐��N�����璷�������ꍇ�̎����G�X�J���[�V�����@�\��ǉ�
    �]������MasterNode�𕡐��ŏ璷���͏o�������A���̏ꍇMasterNode���Ƀ��C���ƂȂ�m�[�h�����݂��A
    �c��̃}�X�^�[�m�[�h�̓X���[�u�Ƃ��������������B
    ���C���̃m�[�h���_�E�������ꍇ�́A�X���[�u�m�[�h�ɃA�N�Z�X����΃f�[�^�̎擾�A�o�^�A�폜���S�Ă�
    �N���C�A���g����͎��s�ł������ADataNode�̊Ď��A�f�[�^�m�[�h�_�E����̋N�����̃f�[�^���J�o�[��
    �X���[�uMasterNode�����ł͎��s����Ȃ������B
    ���̏ꍇ�́A�X���[�uMasterNode�̓���1�C���X�^���X��MasterNode.properties���̐ݒ�l
    "MainMasterNodeMode"��"true"�ɕς���K�v���������B
    ����̉��C�Ń��C��MasterNode���_�E�������ꍇ�̓X���[�uMasterNode�����玩���I��1�C���X�^���X��
    ���C��MasterNode�ɏ��i����悤�ɉ��C�B
    ���̉��C�ɂ��MasterNode.properties�ɐݒ荀�ڂ��ǉ�����A�]���̐ݒ荀�ڂ��g�p�\�ł͂��邪�A��������Ȃ��Ȃ����B

    ���ǉ����ꂽ���ڂ͈ȉ�
      �ESystemConfigMode
        ����) �ݒ�����擾����ꏊ(file or node)
              �ݒ����{�t�@�C�����N������Q�Ƃ������邩�A�N����͖{�t�@�C������x�����Q�Ƃ��A
              �Ȍ�́ADataNode�ɓo�^����Ă���ݒ�����Q�Ƃ��邩�����肷��
              "file"�̏ꍇ�͖{�t�@�C�����Q�Ƃ���
              "node"�̏ꍇ��DataNode���Q�Ƃ���
              �ݒ�����Ȃ��ꍇ��"node"�ƂȂ�
        �L�q��)
             SystemConfigMode=node


      �EMyNodeInfo=127.0.0.1:8888
        ����) ���g�̏��
              ���g��IP�ƋN���|�[�g�ԍ���":"��؂�ŋL�q
              ���g�p�𐄏�
              �����̐ݒ肪�Ȃ��ꍇ�̓��C��MasterNode�̎������i�@�\���@�\���Ȃ�
        �L�q��)
             MyNodeInfo=127.0.0.1:8888


      �EMainMasterNodeInfo
        ����) ���C���}�X�^�[�m�[�h�̏��
              �N�����Ƀ��C��MasterNode�Ƃ��ĔF������m�[�h��IP�ƃ|�[�g�ԍ�
              ���g�����C��MasterNode�̏ꍇ�͎��g�̏����L�q
              ���g�p�𐄏�
        �L�q��)
             MainMasterNodeInfo=127.0.0.1:8888


      �EAllMasterNodeInfo
        ����) �S�Ẵ}�X�^�[�m�[�h�̏��
              �S�Ẵ}�X�^�[�m�[�h�̏��"IP:PORT�ԍ�"�t�H�[�}�b�g��","��؂�ŋL�q 
              ���g�̏���MyNodeInfo�ݒ�̓��e�Ɠ����ł��邱��
              �����ł̋L�q���Ń��C��MasterNode�Ƃ��ċ@�\����
              ���g�p�𐄏�
              �����̐ݒ肪�Ȃ��ꍇ�̓��C��MasterNode�̎������i�@�\���@�\���Ȃ�
        �L�q��)
             AllMasterNodeInfo=127.0.0.1:8888,127.0.0.1:8889,127.0.0.1:11211

    ���g�p����������Ȃ��Ȃ�������
      �EMainMasterNodeMode
      �ESlaveMasterNodes


  ��MasterNode���g�p����ݒ����MasterNode.properties����Q�Ƃ��邾���łȂ��A
    DataNode�ɐݒ�����i�[�������炩�Q�Ƃ���悤�ɉ��C
    �]���ݒ����MasterNode.properties����˂ɎQ�Ƃ��Ă������A�ݒ����DataNode�Ɋi�[����
    �悤�ɉ��C���A�SMasterNode���������L�����ɉ��C
    �������A�N������DataNode�̏�񂪕�����Ȃ����߁AMasterNode.properties����Q�Ƃ���
    �]���ʂ�AMasterNode.properties�݂̂ŉ^�p���邱�Ƃ��\
    ���̐ݒ��ύX����ɂ�MasterNode.properties�̈ȉ��̐ݒ荀�ڂŕύX�o����B
    ���ǉ����ꂽ����
      �ESystemConfigMode
        ����) �ݒ�����擾����ꏊ(file or node)
              �ݒ����{�t�@�C�����N������Q�Ƃ������邩�A�N����͖{�t�@�C������x�����Q�Ƃ��A
              �Ȍ�́ADataNode�ɓo�^����Ă���ݒ�����Q�Ƃ��邩�����肷��
              "file"�̏ꍇ�͖{�t�@�C�����Q�Ƃ���
              "node"�̏ꍇ��DataNode���Q�Ƃ���
              �ݒ�����Ȃ��ꍇ��"node"�ƂȂ�
        �L�q��)
             SystemConfigMode=node



  ��okuyama�Ǘ�Web�R���\�[���A�v���P�[�V������ǉ�
    �ғ�����okuyama�̏󋵊m�F�Ɛݒ�̕ύX���o����ł���Web�A�v�����쐬�B
    �����[�X����execOkuyamaManager.bat�����s����ƊǗ�Web�A�v�����N������B

    URL : http://�N���}�V����IP:10088/okuyamamgr
    �ŃA�N�Z�X�ł���B

    ��execOkuyamaManager.bat���ŋN���|�[�g�ԍ�(10088��)��Web�A�v���������Q�Ƃ���MasterNode��IP:PORT��","��؂�œn���Ă��܂��B
      MasterNode�̏���MasterNode.properties�̐ݒ���"AllMasterNodeInfo"�̓��e�Ɠ��l�ɂ��Ă��������B


========================================================================================================
========================================================================================================
[New - �@�\���P]
[[�����[�X Ver 0.6.6 - (2010/06/08)]]
  ���f�[�^�m�[�h���J�o�[���̎擾�A�o�^�A�폜�̏����̑҂����Ԃ��y��
    �f�[�^�m�[�h�A�X���[�u�f�[�^�m�[�h�̍\���ŉғ����Ă���ꍇ�A�Е��̃m�[�h���_�E�����A
    �N�����Ă���ƁA�Б��̃m�[�h�̃f�[�^���畜�����郊�J�o�[�������s����B�]�����̏������̓N���C�A���g����
    ���Y�m�[�h�̃f�[�^�ɃA�N�Z�X����ƃL���[�C���O����Ă������߁A�҂��ɏ�ԂɂȂ��Ă����B
    ���̕������������A���J�o�[�������̑҂��ƂȂ�^�C�~���O��啝�Ɍy�������B
    ���̂��Ƃɂ��okuyama�̑��g�p���Ԃɑ΂���A�X���[�v�b�g�����コ�ꂽ�B
    �����ɁA�]��������̃m�[�h�̃f�[�^��okuyama�������Ŏg�p���Ă���f�[�^Map���V���A���C�Y����
    �����o���Ă������A���̕������g�����U�N�V�������O�Ɠ��������O�ɏ����o���悤�ɕύX�����B
    ���̂��ƂŃ��J�o�[�����̑����v���Ԃ�ጸ���ꂽ�B
    �����̉��P�̓f�[�^�m�[�h�A�X���[�u�f�[�^�m�[�h�������g�p���ĉғ����Ă���ꍇ�ɗL���ł���B

========================================================================================================
========================================================================================================
[New - �@�\���P]
[[�����[�X Ver 0.6.5 - (2010/05/30)]]
  ��Vacuum�������̎擾�A�o�^�A�폜�̏������p���ł���p�ɉ��C
    �]��Vacuum���͎擾�A�o�^�A�폜�����͏������u���b�N�����悤�ɂȂ��Ă���(�����͑҂���ԂɂȂ�)��
    ���̃u���b�N���Ԃ�啝�ɍ팸����悤�ɉ��C�B
    �]���Ȃ�Vacuum���n�܂�ƏI�n�u���b�N����Ă������A�������p���o����悤��(�҂����������Ȃ�)�Ȃ�
    okuyama�̑��g�p���Ԃɑ΂���A�X���[�v�b�g�����コ���B
    ��Vacuum��DataNode.properties��"KeyManagerJob1.dataMemory=true"�̗p��value���t�@�C���ɕۑ����Ă���
      �ꍇ�̂ݗL���ƂȂ�B

  ���eproperties�t�@�C���ɃR�����g��ǉ�
    MasterNode.properties�ADataNode.properties�ATransactionNode.properties�ɃR�����g��啝�ɒǋL
    �����܂ŃR�����g�����Ȃ��Đ\���󂠂�܂���ł����B

  ��ReadMe.txt�AReadMe-UTF.txt��[���@�\�����ƃT���v���̎��s���@]�A[�T���v���̎��s���@]������ǋL
    TransactionNode�̎g�p���@�AMemcached�݊��ł̋N�����@�A�N���C�A���g�̎g�p�\���\�b�h�������C��
    ���ULock�AsetNewValue(memcache��add����)�̎g�p�T���v����ǋL

========================================================================================================
========================================================================================================
[New - �@�\���P]
[[�����[�X Ver 0.6.4 - (2010/05/21)]]
  ���f�[�^�̕ۑ�����Key�l���ۑ�����悤�ɕύX
    �]����Key�l���琶������Hash�l��ۑ����Ă���������Ȃ��Ⴂ�\���ł͂��邪�Փ˂��N�����\��������ׁA
    ��ʂ̃f�[�^�ۑ��Ɍ����Ȃ��̂ŁAKey�l��������Ƃ��ĕۑ�����悤�ɕύX�B
    ������Key�l�ɂ������̐�����t���B(2048byte)
    �����̕ύX�ɂ���ϐ\���󂠂�܂��񂪁A�]���o�[�W�����ł̃f�[�^�͎g�p�ł��Ȃ��Ȃ�܂��B

  �����������������������A��������������B
    

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.6.3 - (2010/05/19)]]
  ���f�[�^�m�[�h��memcache�̃m�[�h�Ƃ��ė��p�\��
    �}�X�^�[�m�[�h���N�������ɒ��ڃf�[�^�m�[�h��memcache�̃m�[�h�Ƃ��ė��p�\�B
    �ȉ��̂悤�ɐݒ��ύX��execDataNode.bat�����s�����memcache�N���C�A���g�ŃA�N�Z�X�ł���B

    �ݒ�t�@�C��DataNode.properties��25�s��
    ----------------------------------
    KeyManagerHelper.Init=
              ����L�����L���e�ɕύX
    KeyManagerHelper.Init=memcache
    ----------------------------------
    �Ƃ��ċN�������memcache�v���g�R���ŉ�b���\�ƂȂ�B
    �Ή����\�b�h�̓}�X�^�[�m�[�h��memcache���[�h�Ƃ��ċN�������ꍇ�Ɠ��l�ƂȂ�B
    (�Eset, �Eget, �Eadd, �Edelete)(flag�ɑΉ�)

    �t�@�C���ւ̃f�[�^�i�������\
    �ݒ�t�@�C��DataNode.properties��30�s�ځA31�s��
    ----------------------------------
    KeyManagerJob1.memoryMode=false       
    KeyManagerJob1.dataMemory=true
    ----------------------------------
    ��L�̐ݒ�Ńg�����U�N�V�������O�͎c���A�o�^���ꂽ�f�[�^�̓������ɕێ�����
    ������true�ɂ���Ɗ��S���������[�h(�ł������ɉғ�)(�P�̂�memcache�Ƃقړ����x�̏������x���o��)
    ������false�ɂ���Ɗ��S�t�@�C�����[�h(�ł���ʂ̃f�[�^(Value�̃T�C�Y)��ێ��\)

    �f�t�H���g�ł�2560�o�C�g��value�T�C�Y�̍ő�l�ƂȂ�̂ŁAsrc\org\imdst\util\ImdstDefine.java��150�s�ڂ�
    �ύX��compile.bat�����s���R���p�C������Ƌ��e�ł���f�[�^�T�C�Y���ύX�ł���B


  ��Key�l����Hash�l�����߂郍�W�b�N��ύX
    okuyama�ł͓o�^���ꂽKey�l�̓n�b�V���l�����߂Ă��̒l�����ۂ̓o�^�Ɏg�p���Ă��邪�A
    ���̒l�̐������W�b�N���������A���n�b�V���l�����U����悤�ɕύX
    ���̕ύX�ɂ��A���܂œo�^�����f�[�^�͑S�Ĕj������K�v������܂��B
    ���̕ύX���󂯓�����Ȃ��ꍇ��src\org\imdst\helper\MasterManagerHelper.java��2660�s�ځA2661�s�ڂ�
    �ȉ��̂悤�ɕύX���Acompile.bat�����s���ăR���p�C�������s�B
    --------------------------------------------------------------------
	private int hashCodeCnv(String str) {
		return new HashCodeBuilder(17,37).append(str).toHashCode();
		//return str.hashCode();
	}
               ���������ύX(�R�����g�A�E�g�����ւ�)
	private int hashCodeCnv(String str) {
		//return new HashCodeBuilder(17,37).append(str).toHashCode();
		return str.hashCode();
	}
    --------------------------------------------------------------------


  ���f�[�^�o�^���\�b�hsetValue���̏������x��20%����
    �f�[�^�m�[�h�A�X���[�u�f�[�^�m�[�h�N������setValue�����s�����ۂ̏������x��
    20%����B�f�[�^�m�[�h�ւ̓o�^���N�G�X�g���M���ɃX���[�u�f�[�^�m�[�h�ւ̑��M����������悤�ɏC���B


  ���o�OFix
    

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.6.2 - (2010/05/09)]]
  ��Memcahe�v���g�R�����[�h���̈ȉ��̏�����Ή�
    1.memcache�̃��\�b�h�ł���add�ɑΉ�
      ���o�^�f�[�^�̏ꍇ�̂ݓo�^�\��memcache��add�R�}���h�ɑΉ�

    2.memcache�̃��\�b�h�ł���delete�ɑΉ�
      memcache�R�}���h�ł���f�[�^�폜�p�R�}���hdelete�ɑΉ�

    3.memcache��flag�o�^�ɑΉ�
      memcache�R�}���h��set�Aadd���Ɏw�肷��flag�ɑΉ�
      get���ɓo�^flag��ԋp

  ���f�[�^�m�[�h�Ԃ̃f�[�^���J�o�[���̃f�[�^�]���������ꕔ�ύX
    �]���̓m�[�h�_�E������̃��J�o�[���Ƀ��v���P�[�V�����m�[�h�����1�ʐM�őS�Ă̓o�^�f�[�^�擾���Ă��������A
    ����ł͑傫�ȃf�[�^���o�^����Ă���ꍇ�ɁA���M���A��M���Ń������ɂ̂肫�炸�Ƀ��J�o�[�Ɏ��s����ꍇ��
    ���������߁A�g�p�\�ȃ������̎c�ʂ��m�F���Ȃ���A�f�[�^�𕪊����ē]�������J�o���[����悤�ɕύX
    ���f�[�^�̕ۑ��������������ł͂Ȃ��t�@�C���ɂ��Ă���ꍇ�́A���ɂ��̖��͔�������\�����������B

  ��PHP�p�N���C�A���g(OkuyamaClient.class.php)��getByteValue���\�b�h��ǉ�
    Java�p�N���C�A���g�œo�^�����o�C�g�f�[�^(setByteValue�œo�^�����f�[�^)���擾����ۂɎg�p

  ���o�OFix

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.6.1 - (2010/04/21)]]
  ���f�[�^�����݂��Ȃ��ꍇ�̂ݕۑ��ł��郁�\�b�h��ǉ�
    +���o�^�̃L�[�l�ł���ꍇ�̂ݓo�^�\�ƂȂ�A���ɓo�^�ς݂̏ꍇ�͓o�^�ł��Ȃ��B

     *���o�^�̏ꍇ�̂ݓo�^�\�ȃ��\�b�h�͈ȉ��ł���B
      �E�N���C�A���g�̃��\�b�h��:setNewValue
      �E����1:Key�l
      �E����2:Value�l
      �E�߂�l:String[] �v�f1(�f�[�^�L��):"true" or "false",�v�f2(���s���̓��b�Z�[�W):"���b�Z�[�W"

      �E�N���C�A���g�̃��\�b�h��:setNewValue
      �E����1:String Key�l
      �E����2:String[] tag�l�z��
      �E����3:String Value�l
      �E�߂�l:String[] �v�f1(�f�[�^�L��):"true" or "false",�v�f2(���s���̓��b�Z�[�W):"���b�Z�[�W"

  ���N���C�A���g����ڑ����ɕۑ��o����ő�f�[�^�T�C�Y��MasterNode����擾����悤�ɕύX

  ���o�OFix

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.6.0 - (2010/04/08)]]
  �����U���b�N�@�\��ǉ�
    +�C�ӂ̃f�[�^�����b�N����@�\��ǉ��B
    +���U���b�N�@�\�̓}�X�^�[�m�[�h�p�ݒ�t�@�C���ł���AMasterNode.properties��9�s�ڂ�"TransactionMode=true"��
     ���b�N�@�\���g�p�\�ƂȂ�B
     �܂��A72�s�ڂ�"TransactionManagerInfo=127.0.0.1:6655"��TransactionManager�m�[�h���w�肷��K�v������
     �����āATransactionManager�m�[�h���N�����Ă���K�v�����邽�߁A������execTransactionNode.bat�ŋN������B
     ���U���b�N�@�\���g�p����ꍇ�́A�S�Ẵ}�X�^�[�m�[�h��"TransactionMode=true"�ŋN�����Ă���K�v������B
     �����̐ݒ�t�@�C���͑S�ĕ��U���b�N�@�\�ŋN������ݒ�ƂȂ�B
     ��execMasterNodeMemcached.bat�͕��U���b�N�@�\����Amemcache�v���g�R�����[�h�ŋN������B
     �܂��A�]���̕��U���b�N�@�\�Ȃ��ŋN������ꍇ�́A"TransactionMode=false"�Ƃ���execMasterNode.bat�����s����B

    +�d�g�݂Ƃ��ẮAClient���烍�b�N�擾�˗����s�����ꍇ�ATransactionManager�m�[�h�Ɏw�肵��Key�l��
     ���b�N�������グ��B���̍ہA���łɕ�Client���瓯���Key�l�Ń��b�N���擾����Ă���ꍇ�́A
     �w�肵�����Ԃ̊ԁA���b�N�����������̂�҂��A�擾�����݂�B
     ���b�N���ꂽ�l�ɑ΂��āAset,remove�n�̃A�N�Z�X���s�����ꍇ�́ATransactionManager�m�[�h�ɑ΂��ĊY����
     Key�l���A���N�G�X�g�𔭍s����Client�ȊO���烍�b�N����Ă��邩��₢���킹�āA�ʃN���C�A���g�����b�N
     ���Ă���ꍇ�́A���b�N�����������̂�҂�������B
     ���N���C�A���g�����b�N���Ă���������́A���b�N���Ȃ��ꍇ�́A���̂܂܏����𑱍s����B
     ���b�N�̃����[�X�����������ł���B
     �Ȃ��A���U���b�N�@�\��L���ɂ����ꍇ�́A�������Ɣ��1��ʐM�������������邽�߁A�������x�͗�����B
     �܂��ATransactionManager�m�[�h��SPOF�ƂȂ邪�A�@�\���Ă��Ȃ��ꍇ�͖������ĉғ����邪�A
     �������x�͋ɒ[�ɗ򉻂���B
     ����ASPOF�ƂȂ�Ȃ��悤�ɉ��P�\��ł���B

    +�ȉ��͐����ƂȂ�
     *���b�N�����{�����f�[�^�̋����͈ȉ��ƂȂ�B
      �E���b�N�\��Key�l(�f�[�^)�͌��ݓo�^�ς݂ł����Ă��A�o�^����Ă��Ȃ��Ă��\�ł���B
      �E1�N���C�A���g���瓯���ɕ����̃f�[�^�����b�N�\�ł���
      �E���b�N�����f�[�^�̓��b�N�����{�����N���C�A���g����̂݃��b�N�����\�ł���B
      �E���b�N���̃f�[�^�̓��b�N�����{�����N���C�A���g����̂ݓo�^�\�ł���B
      �E���b�N���̃f�[�^�̓��b�N�����{�����N���C�A���g����̂ݕύX�\�ł���B
      �E���b�N���̃f�[�^�̓��b�N�����{�����N���C�A���g����̂ݍ폜�\�ł���B
      �E���b�N���̃f�[�^�͑S�N���C�A���g����Q�Ɖ\�ł���B
 
     *���b�N�@�\�g�p�J�n���\�b�h�͈ȉ��ł���B
      �E�N���C�A���g�̃��\�b�h��:startTransaction
      �E�����Ȃ�
      �E�߂�l:boolean true:�X�^�[�g���� false:�X�^�[�g���s
        �����b�N�@�\�L���TransactionManager�m�[�h���N�����Ă��Ȃ��ꍇ�́A�X�^�[�g�Ɏ��s����B
          
     *���b�N���\�b�h�ւ̈����Ɩ߂�l�͈ȉ��ł���B
      �E�N���C�A���g�̃��\�b�h��:lockData
      �E����1:���b�N�Ώ�Key�l
        ����2:���b�N�p������
              (���b�N�������s��Ȃ��ꍇ�ł��A�����ł̐ݒ莞�Ԃ��o�߂���Ǝ����I�ɉ��������B
               �P�ʂ͕b�B
               0��ݒ肷��ƃ��b�N�����{�����N���C�A���g����������܂ŉi�v�Ƀ��b�N�����B
               ��0�w��͐������Ȃ�)
        ����3:���b�N�擾�҂�����
              (���ɕʃN���C�A���g�����b�N���̃f�[�^�փ��b�N�����{�����ꍇ�ɁA�ݒ莞�Ԃ̊ԃ��b�N�擾�����g���C����B
               �P�ʂ͕b�B
               0��ݒ肷���1�񃍃b�N�����݂�)
 
      �E�߂�l:String�z��
               String�z��[0]:Lock���� "true"=Lock���� or "false"=Lock���s
 
 	 *���b�N�J���ւ̈����Ɩ߂�l�͈ȉ��ł���B
      �E�N���C�A���g�̃��\�b�h��:releaseLockData
      �E����1:���b�N�Ώ�Key�l
 
      �E�߂�l:String�z��
               String�z��[0]:�J������ "true"=�J������ or "false"=�J�����s

     *���b�N�@�\�g�p�I�����\�b�h�͈ȉ��ł���B
      �E�N���C�A���g�̃��\�b�h��:endTransaction
      �E�����Ȃ�
      �E�߂�l�Ȃ�

    +Java�ŁAPHP�ł̃N���C�A���g����́A���b�N�A�����[�X�������\
     Memchache�N���C�A���g�̓��b�N�A�����[�X�@�\�͗��p�ł��Ȃ����ALock���̃f�[�^��set�����s�����ꍇ��"�҂����"�ɓ���B

   ����ImdstKeyValueClient���g�p����������)��������������������������������������������������������������������
   ��                                                                                                        ��
   �� // �N���C�A���g�C���X�^���X�쐬                                                                        ��
   �� ImdstKeyValueClient client = new ImdstKeyValueClient();                                                ��
   �� // �ڑ�                                                                                                ��
   �� imdstKeyValueClient.connect("127.0.0.1", 8888);                                                        ��
   �� // Transaction���J�n���ăf�[�^��Lock��A�f�[�^���X�V�A�擾���ALock������                               ��
   ��                                                                                                        ��
   �� // ������Lock�Ώۂ�Key�l, Lock�ێ�����(�b)(0�͖�����), Lock�����Ɏ擾����Ă���ꍇ��                  ��
   �� // �擾���g���C�������鎞��(�b)(0��1��擾�����݂�)                                                    ��
   �� ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();                                   ��
   �� imdstKeyValueClient.connect(args[1], port);                                                            ��
   �� String[] ret = null;                                                                                   ��
   ��                                                                                                        ��
   �� // Lock����                                                                                            ��
   �� if(!imdstKeyValueClient.startTransaction()) throw new Exception("Transaction Start Error!!");          ��
   ��                                                                                                        ��
   �� long start = new Date().getTime();                                                                     ��
   ��                                                                                                        ��
   �� // Lock���s                                                                                            ��
   �� // "DataKey"�Ƃ���Key�l��10�b�Ԉێ����郍�b�N���쐬�B�������Ƀ��b�N����Ă���ꍇ�́A5�b�ԃ��b�N�擾�� ��
   �� // �J��Ԃ�                                                                                            ��
   �� ret = imdstKeyValueClient.lockData("DataKey", 10, 5);                                                  ��
   �� if (ret[0].equals("true")) {                                                                           ��
   ��     System.out.println("Lock����");                                                                    ��
   �� } else if (ret[0].equals("false")) {                                                                   ��
   ��     System.out.println("Lock���s");                                                                    ��
   �� }                                                                                                      ��
   ��                                                                                                        ��
   ��                                                                                                        ��
   �� // �ȉ��̃R�����g�A�E�g���͂����āA�R���p�C�����A                                                      ��
   �� // �ʂ̃N���C�A���g����X�V�����s����ƁA�X�V�ł��Ȃ��̂��킩��                                        ��
   �� //Thread.sleep(5000);                                                                                  ��
   ��                                                                                                        ��
   �� // ���g�Ń��b�N���Ă���̂ōX�V�\                                                                    ��
   �� if (!imdstKeyValueClient.setValue(args[3], "LockDataValue")) {                                         ��
   �� 	System.out.println("�o�^���s");                                                                      ��
   �� }                                                                                                      ��
   ��                                                                                                        ��
   �� // �擾                                                                                                ��
   �� ret = imdstKeyValueClient.getValue(args[3]);                                                           ��
   �� if (ret[0].equals("true")) {                                                                           ��
   ��     // �f�[�^�L��                                                                                      ��
   ��     System.out.println("Lock���ɓo�^�����f�[�^[" + ret[1] + "]");                                      ��
   �� } else if (ret[0].equals("false")) {                                                                   ��
   ��     System.out.println("�f�[�^�Ȃ�");                                                                  ��
   �� } else if (ret[0].equals("error")) {                                                                   ��
   ��     System.out.println(ret[1]);                                                                        ��
   �� }                                                                                                      ��
   ��                                                                                                        ��
   �� // ���g�Ń��b�N���Ă���̂ō폜�\                                                                    ��
   �� ret = imdstKeyValueClient.removeValue(args[3]);                                                        ��
   ��                                                                                                        ��
   �� if (ret[0].equals("true")) {                                                                           ��
   ��     // �f�[�^�L��                                                                                      ��
   ��     System.out.println("Lock���ɍ폜�����f�[�^[" + ret[1] + "]");                                      ��
   �� } else if (ret[0].equals("false")) {                                                                   ��
   ��     System.out.println("�f�[�^�Ȃ�");                                                                  ��
   �� } else if (ret[0].equals("error")) {                                                                   ��
   ��     System.out.println(ret[1]);                                                                        ��
   �� }                                                                                                      ��
   ��                                                                                                        ��
   �� // Lock�J��                                                                                            ��
   �� ret = imdstKeyValueClient.releaseLockData(args[3]);                                                    ��
   �� if (ret[0].equals("true")) {                                                                           ��
   ��     System.out.println("Lock�J������");                                                                ��
   �� } else if (ret[0].equals("false")) {                                                                   ��
   ��     System.out.println("Lock�J�����s");                                                                ��
   �� }                                                                                                      ��
   ��                                                                                                        ��
   �� long end = new Date().getTime();                                                                       ��
   �� System.out.println((end - start) + "milli second");                                                    ��
   ��                                                                                                        ��
   �� // �g�����U�N�V�����J��                                                                                ��
   �� imdstKeyValueClient.endTransaction();                                                                  ��
   �� // �ڑ��ؒf                                                                                            ��
   �� imdstKeyValueClient.close();                                                                           ��
   ������������������������������������������������������������������������������������������������������������

  ���������̃o�O���C��

  ������́A���U�g�����U�N�V��������������悤�Ɏ�����i�߂�B
========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.5.2 - (2010/03/28)]]
  ��Memcache�v���g�R���Ɉꕔ�Ή�
    KVS�̕W���v���g�R���ɂȂ����Amemcache�̃v���g�R���ɑΉ����郂�[�h��ǉ�
    MasterNode.properties��14�s��"MasterManagerJob.Option="��"MasterManagerJob.Option=memcache"�Ƃ����
    memcache�v���g�R���ŃA�N�Z�X�\�ł���B
    MasterNode2.properties��memcache�p�̐ݒ�t�@�C���ɂȂ��Ă���B
    execMasterNodeMemcached.bat�����s�����memcache�v���g�R���ŗ����オ��B
    �Ή����\�b�h��set��get�ł���B�܂�set,get��flag��0�̂ݑΉ����Ă���B
    ����Ή��͈͂𑝂₷�\��B

  ���f�[�^�ۑ��`�����t�@�C��(DataNode.properties30�s�ځA33�s�ڂ�false�Ƃ����ꍇ)�ɂ����ꍇ�ɁA
    �ǋL�^�ŋL�����Ă��邽�߁A�t�@�C�����i���ɔ�剻���邽�߁Avacuum�@�\��ǉ��B
    �����I�Ɏ��s�����B

  ��documet�f�B���N�g����ǉ�
    ���\�]�������{���������ƁA�\���}��z�u

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.5.1 - (2010/03/17)]]
  ��PHP�p�̃N���C�A���g���쐬
	PHP��MasterServer�փA�N�Z�X�o����悤�ɃN���C�A���g���쐬�B
	Java�̃R�[�h���Ă��Ȃ����܂����B
	�o�C�g�f�[�^��o�^(setByteValue)�A�擾(getByteValue)���郁�\�b�h�̂ݖ������B
    �����[�X��etc_client\OkuyamaClient.class.php�ɂȂ�܂��B
    �T���v�����s�R�[�hetc_client\PhpTestSock.php�ƁA���s�pbat�t�@�C��etc_client\PhpAutoTest.bat�𓯍����܂����B

  ��ReadMe.txt�AReadMe-UTF.txt���ŐV�̏�ԂɍX�V

  ��ReadMe.txt�AReadMe-UTF.txt��"[[�����[�X Ver 0.5.0 - (2010/03/17)]]"�̋L�q�~�X�����
    �����ӏ��͈ȉ�
    -------------------------------------------------------------------------------------------------------------------------------------------
    ��TestSock�T���v����Script���s���[�h�̃o�[�W������ǉ�(���� "2.3" Script���s)
    �E�擾�A���s�T���v���N�����@
    java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2.3 127.0.0.1:8888 20000 "var dataValue; var retValue = dataValue.replace('data', 'dummy'); var execRet = '1';"
                                                                        ^^^
                                                               �������� 127.0.0.1 8888
    -------------------------------------------------------------------------------------------------------------------------------------------

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.5.0 - (2010/03/17)]]
  ���f�[�^�擾����JavaScript�����s�\�ȃC���^�[�t�F�[�X��ǉ�
    ImdstKeyValueClient��getValueScript���\�b�h�Ŏ��s�\�B
    �f�[�^�擾����JavaScript�ŋL�q�����X�N���v�g��Key�l�Ɠ����ɓn���AKey�l��Value�l���擾�o�����ꍇ�A
    ���̒l�ɃX�N���v�g�����s�����̌��ʂ��擾�ł���B
    �X�N���v�g���ŁA�ԋp�L���̌���y�сA�ԋp�l(Value)��ݒ肷�邱�Ƃ��o����B
    �X�N���v�g�̓f�[�^�m�[�h�Ŏ��s����邽�߁A���܂Ŏ擾�����f�[�^�ɑ΂��ĉ������̏����ŉ��Hor�l�̑Ó���
    ���؂Ȃǂ��s���Ă����ꍇ�́A�X�N���v�g�ŏ������s���A�擾�}�V���̃��\�[�X�̐ߖ��A�擾�}�V����
    �X�y�b�N���z����悤�ȑ�K�͂ȃf�[�^���f�[�^�m�[�h�̃p���[���g�p���ď����\�ł���B

    �y�X�N���v�g�L�q����z
     �X�N���v�g�̐���͈ȉ��̖��O�̕ϐ���錾����K�v������B
    �E "dataValue" = key�l�Ŏ擾�o����value�l���ݒ肳���B�X�N���v�g���ł͂��̕ϐ���value�l�ƂȂ�B
    �E "execRet" = ���s����(retValue�ϐ�)���N���C�A���g�ɕԂ����Ƃ��w��
                   (1��������ƕԋp����� 0��������ƕԋp����Ȃ�)
    �E "retValue" = ���s���ʂ��i�[����B�N���C�A���g�ɕԂ����l

   ����ImdstKeyValueClient���g�p����������)��������������������������������������������������������������������
   ��                                                                                                        ��
   �� StringBuffer scriptBuf = new StringBuffer();                                                           ��
   �� // �X�N���v�g���쐬                                                                                    ��
   �� scriptBuf.append("var dataValue;");                                                                    ��
   �� scriptBuf.append("var execRet;");                                                                      ��
   �� scriptBuf.append("var retValue;");                                                                     ��
   �� // �擾����Value�l��"data"�Ƃ�������������ꍇ��"dummy"�ɒu������                                      ��
   �� scriptBuf.append("retValue = dataValue.replace('data', 'dummy');");                                    ��
   �� // �ԋp�w��                                                                                            ��
   �� scriptBuf.append("execRet = '1';");                                                                     ��
   ��                                                                                                        ��
   �� // �N���C�A���g�C���X�^���X�쐬                                                                        ��
   �� ImdstKeyValueClient client = new ImdstKeyValueClient();                                                ��
   �� // �ڑ�                                                                                                ��
   �� imdstKeyValueClient.connect("127.0.0.1", 8888);                                                        ��
   �� // Value�擾�y�сA�X�N���v�g���s���˗�                                                                 ��
   �� String[] retValue = imdstKeyValueClient.getValueScript("key1", scriptBuf.toString());                  ��
   ��                                                                                                        ��
   �� // ���ʂ�\��                                                                                          ��
   �� // ���s���ʂ����݂���ꍇ��"true"�����݂��Ȃ��ꍇ��"false"���A�G���[�̏ꍇ��"error"���ԋp�����        ��
   �� System.out.println(retValue[0]);                                                                       ��
   �� // retValue[0]��"true"�̏ꍇ�̓X�N���v�g����̕ԋp�l���ԋp�����B"error"�̏ꍇ�̓G���[���b�Z�[�W���ԋp��
   �� System.out.println(retValue[1]);                                                                       ��
   ������������������������������������������������������������������������������������������������������������

  ��TestSock�T���v����Script���s���[�h�̃o�[�W������ǉ�(���� "2.3" Script���s)
    �E�擾�A���s�T���v���N�����@
    java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2.3 127.0.0.1 8888 20000 "var dataValue; var retValue = dataValue.replace('data', 'dummy'); var execRet = '1';"

  ���N���C�A���g�Ƀ}�X�^�[�m�[�h�̎����o�������V���O���[�h�y�сA�_�E�����̍Đڑ��@�\��ǉ�
    ImdstKeyValueClient��setConnectionInfos���\�b�h�ɐڑ��ΏۂƂȂ�}�X�^�[�m�[�h�̐ڑ��������z���
    �Z�b�g(�t�H�[�}�b�g"IP:PORT�ԍ�"��String�z��)���AautoConnect���\�b�h�Őڑ�����ƁA�m�[�h�ւ̐ڑ���
    �o���Ȃ��ꍇ�A�ڑ��㏈���r���Őؒf���ꂽ�ꍇ�Ȃǂ��A�����I�ɍĐڑ����ғ��������邱�Ƃ��o����B


  ��TestSock�T���v���Ɏ����ڑ����[�h�̃o�[�W������ǉ�(���� "1.2"�����ڑ��œo�^  "2.2"�����ڑ��Ŏ擾)
    �E�o�^�T���v���N�����@
    java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 1.2 "127.0.0.1:8888,127.0.0.1:8889" 20000
    �E�擾�T���v���N�����@
    java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2.2 "127.0.0.1:8888,127.0.0.1:8889" 20000

    ��execMasterNode.bat��execMasterNode2.bat�𓯎��Ɏ��s������Ԃŏ�L�����s���āA�Б�����~���ẮA
      �Ď��s���J��Ԃ��Ă��A�������ғ��������邱�Ƃ��m�F�ł��܂��B

========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.4.0 - (2010/03/15)]]
 ���f�[�^�m�[�h�̓��I�ǉ����T�|�[�g
   �}�X�^�[�m�[�h�A�f�[�^�m�[�h�N������MasterNode.properties��KeyMapNodesRule�AKeyMapNodesInfo�A
   SubKeyMapNodesInfo�ɐV���ȃm�[�h�̋L�q��ǋL���ۑ�����ƁA�����I�Ƀt�@�C�����ēǂݍ��݂���A
   �f�[�^�A�X���[�u���m�[�h���ǉ������B
   �ݒ�t�@�C���͍ĕۑ������Ƃقڃ��A���^�C���ɔ��f����邽�߁A�ۑ��O�ɊY���m�[�h���N�����Ă����K�v������B
    �����ݒ�                                                ���m�[�h�ǉ�
   ���� MasterNode.properties����������������������������  ���� MasterNode.properties����������������������������������������������������������
   ��KeyMapNodesRule=2                                 ��  ��KeyMapNodesRule=4,2                                                             ��
   ��                                                  ��  ��                                                                                ��
   ��KeyMapNodesInfo=localhost:5553,localhost:5554     ��=>��KeyMapNodesInfo=localhost:5553,localhost:5554,localhost:6553,localhost:6554     ��
   ��                                                  ���ۄ�                                                                                ��
   ��SubKeyMapNodesInfo=localhost:5556,localhost:5557  ������SubKeyMapNodesInfo=localhost:5556,localhost:5557,localhost:6556,localhost:6557  ��                        ��
   ��                                                  ��  ��                                                                                ��
   ������������������������������������������������������  ������������������������������������������������������������������������������������

 ���f�[�^�m�[�h�ǉ���ɐV�����m�[�h�փf�[�^�̈ڍs���s���@�\��ǉ�
   �f�[�^�m�[�h�ǉ���ɉߋ��f�[�^�m�[�h�䐔�^�p���̃f�[�^�ɃA�N�Z�X�����^�C�~���O�Œǉ���m�[�h��
   ���C���f�[�^�m�[�h�A�X���[�u�f�[�^�m�[�h�փf�[�^�������I�ɕۑ�����悤�ɂ��A�Ȍ�ߋ��̃f�[�^�ۑ��m�[�h��
   �A�N�Z�X���s��Ȃ��悤�ɋ@�\��ǉ��B
   ���m�[�h�ǉ����s���Ǝ����I�Ƀf�[�^�A�N�Z�X���ɍs����B

 ���f�[�^�m�[�h�ւ̃A�N�Z�X�����C���f�[�^�m�[�h�A�X���[�u�f�[�^�m�[�h�ԂŃo�����V���O�o���郂�[�h��ǉ�
   MasterNode.properties��LoadBalanceMode�̐ݒ��true�ɂ���ƃo�����V���O���s���B
   ���C���ƁA�X���[�u�Ő��\���傫���قȂ�ꍇ�̓o�����V���O���s��Ȃ��ق����ǂ��ꍇ������B
   �U�蕪���͒P���ȃ��E���h���r�������ł���B

 ���}�X�^�[�m�[�h�𕡐���ғ������A���ו��U�A�璷���o����@�\��ǉ�
   ���܂ł́A�}�X�^�[�m�[�h��1��\�����������ASPOF�ƂȂ��Ă����ׁA������N���o����悤�ɋ@�\�ǉ��B
   �}�X�^�[�m�[�h��1�`n��ł̍\�����\�����A1��͕K���}�X�^�[�m�[�h���ł̃��C���ɂȂ�Ȃ���΂Ȃ�Ȃ��B
   ���R�́A�f�[�^�m�[�h�̐����Ď��ƕ������̃��J�o���[�����ׂ̈ł���B
   ���J�o���[�������́A�S�Ẵ}�X�^�[�m�[�h���������ĉғ����邽�߁A�s�����͔������Ȃ��\���ƂȂ��Ă���B
   MasterNode.properties��MainMasterNodeMode�����C���̏ꍇ��true�Ƃ��A�X���[�u�̏ꍇ��false�Ƃ���B
   �܂��A�X���[�u�̃}�X�^�[�m�[�h�̃l�b�g���[�N��̖��O�Ɖғ��|�[�g�ԍ���SlaveMasterNodes�ɃJ���}��؂�ŗ񋓂���B
   ���璷�����Ȃ��ꍇ��MainMasterNodeMode=true�Ƃ��邾���ł悢�B

  �����[�X��\(src or classes)\MasterNode.properties(���C���p) �����[�X��\(src or classes)\MasterNode2.properties(�X���[�u�p)
 ���� MasterNode.properties����������������������������      ���� MasterNode2.properties ��������������������������
 ��MainMasterNodeMode=true                           ��      ��MainMasterNodeMode=false                          ��
 ��                                                  ��      ��                                                  ��
 ��SlaveMasterNodes=127.0.0.1:8889                   ��      ��SlaveMasterNodes=                                 ��
 ��                                                  ��      ��                                                  ��
 ������������������������������������������������������      ������������������������������������������������������

   ���C���̃}�X�^�[�m�[�h�ŁA�f�[�^�m�[�h�̊Ď��A�������s�����A���C���̃}�X�^�[�m�[�h���ғ��o���Ȃ���Ԃ�
   �Ȃ����ꍇ�́A�X���[�u�̃}�X�^�[�m�[�h�̐ݒ�t�@�C�����ȉ��̂悤�ɏ��������čĕۑ�����ƁA
   �X���[�u�̃}�X�^�[�m�[�h�����C���̃}�X�^�[�m�[�h�ɕύX����ĉғ����n�߂�B
 ���� MasterNode2.properties   ����������������������������
 ��MainMasterNodeMode=true                               ��
 ��                                                      ��
 ��SlaveMasterNodes=(�ʂ̃}�X�^�[�m�[�h������ꍇ�͋L�q) ��
 ��                                                      ��
 ����������������������������������������������������������
   ��SlaveMasterNodes�ɗ񋓂����m�[�h���ғ����Ă��Ȃ��Ă��A���C���m�[�h�������ғ�����B
   �������I�ɃX���[�u�����C���ɏ��i����悤�Ɍ�قǎ����\��B
   ��ImdstKeyValueClient�ɕ����̃}�X�^�[�m�[�h��ݒ�ł���悤�ɂ��A
     �o�����V���O��A�ڑ��ł��Ȃ��ꍇ�̎����ʃm�[�h�Đڑ��@�\�Ȃǂ���قǎ����\��B

 ���N��bat�t�@�C���ǉ�
   execMasterNode2.bat <=�X���[�u�}�X�^�[�m�[�h�N���R�}���h
   execMasterNode.bat�݂̂ł̉ғ��͏]���Ɠ����悤�ɉ\
========================================================================================================
========================================================================================================
[New - �@�\���P]
[[�����[�X Ver 0.3.3 - (2010/03/12)]]
 ���f�[�^�m�[�h���m�̃f�[�^���J�o�����ɏ]���͋N�����̃m�[�h�̃f�[�^���ċN�����Ă����m�[�h��
   �������Ń��J�o�����Ă��������A�f�[�^�̓o�^�A�폜�Ɏ��{�����̗v�f��ǉ����A���J�o�����Ɏ��{������
   �m�F���A�V�����m�[�h�̃f�[�^��K������悤�ɉ��P�B

========================================================================================================
========================================================================================================
[New - �s��C��&�T���v���R�[�h�ǉ�]
[[�����[�X Ver 0.3.2 - (2010/03/10)]]
 ����萔��Key-Value�𓯂�Tag�ɕR�t���ĕۑ�����Ɛ��������o���Ȃ��s����C��

 ��TestSock�ɃL�[�l���w�肵�č폜���郂�[�h��ǉ�(����"8")
   java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 8 127.0.0.1 8888 KeyName1
   ��L��127.0.0.1�̃|�[�g8888�ԂŋN�����Ă���}�X�^�[�m�[�h�ɐڑ����A"keyName1"�Ƃ���Key�l�ŕۑ������
  ����f�[�^���폜����B
========================================================================================================
========================================================================================================
[New - �폜���\�b�h������&�f�[�^�ۑ�����������ƃt�@�C����I���ł���悤�ɋ@�\�ǉ�]
[[�����[�X Ver 0.3.0 - (2010/03/4)]]
 ���폜���\�b�h��ǉ�
   ImdstKeyValueClient��removeValue���\�b�h�ɂČĂяo���\
   ���^�[���l��getValue�Ɠ��l�Ō��ʕ�����("true" or "false")�ƍ폜�ł����ꍇ�͑Ώۂ̒l���i�[���ꂽ�z��
   TestSock��"7"�Ԏw��ŌĂяo���\
   ---------------------------------------------------------------------------------------------------
   java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 1 127.0.0.1 8888 100         <= 100���o�^
   java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 7 127.0.0.1 8888 50         <= 50���폜

 ���f�[�^�ۑ��`�����������ƃt�@�C����I���\
   ���܂ł̃o�[�W�����ł͉ғ����̓f�[�^�͏��Key��Value�̊֌W�Ń�������ɕێ�����Ă����B
   �o�^���̃g�����U�N�V�����L�^�t�@�C���ƁA����I�ȃ��������̃t�@�C�������o���ŉi������ۂ��Ă������A
   �t�@�C�������o�����[�h�ł�Key�̂݃�������ɕێ����f�[�^�̓t�@�C���Ƀ��X�g�A���邱�Ƃ������B
   ����ɂ�胁��������ł̏������Ȃ����邱�Ƃ��\�ł���A�e�X�g�ł�JVM�I�v�V������-Xmx256m�Ƃ���
   DataNode��400�����ȏ�̃f�[�^���i�[�o�����B
   (��Key�l�̒�����DataNode�i�[���͉e�����Ȃ����A�Q�l��"datasavekey_1"�`"datasavekey_4000000"�Ƃ���Key�l)
   �������A���܂Ő����݂��Ă��Ȃ������i�[�f�[�^���ɐ��񂪏o�����B
   ���݊i�[�ł���Value�̃T�C�Y�́A512byte�ł���B
   ����ȏ�̃f�[�^�����i�[����ꍇ�́AImdstKeyValueClient��setByteValue���\�b�h���g�p���邱�ƂƂȂ�B
   ��512�̎w���ύX����ꍇ�͈�x�S�Ẵf�[�^�t�@�C��(�T���v���ł�.\keymapfile�f�B���N�g���̃t�@�C��)��
     �S�č폜���Ă���AImdstDefine��saveDataMaxSize��ύX���邱�ƂőΉ��\�B
   ���f�[�^�t�@�C���ۑ����@�͒ǋL�^�ƂȂ�̂ŁA����o�L���[�����\�b�h�������\��B
   ���������ƃt�@�C���̐؂�ւ���DataNode.properties��
     "KeyManagerJob1.dataMemory=false" <=�t�@�C��
     "KeyManagerJob1.dataMemory=true"  <=������
     �Ő؂�ւ��\

========================================================================================================
========================================================================================================
[New - MasterNode�����������œK��&���\�]���̃e�L�X�g��Y�t]
[[�����[�X Ver 0.2.2 - (2010/02/24)]]
 ��MasterNode�̃��W�b�N���œK���B
   �œK���c�ӏ��͂܂��c���Ă���B

 ���œK���O�ƌ�ŁA�ȒP�ɐ��\�𑪒�B���茋�ʂ��e�L�X�g�Ƃ��ēY�t
========================================================================================================
========================================================================================================
[New - �s��C��]
[[�����[�X Ver 0.2.1 - (2010/02/11)]]
 ���������J�o�[���̋������C���B
   ��~�m�[�h�N�����̃^�C�~���O�ɂ���Đ������f�[�^�����J�o���[����Ȃ��s����C���B

 ��src\MasterNode.properties�ɃR�����g��ǉ��B
========================================================================================================
========================================================================================================
[New - �@�\�ǉ�]
[[�����[�X Ver 0.2.0 - (2010/02/08)]]
 ���������v���P�[�V�����y�сA�������J�o���[�@�\��ǉ�
   �f�[�^�m�[�h�N���b�V�������V�X�e���̋@�\��~��h�~�B

 [�ǉ��@�\�ڍ�]
  1. 1�̃f�[�^�𕡐��̃f�[�^�m�[�h�ɓo�^����悤�@�\��ǉ�(�������v���P�[�V����)
     ���U�o�^���s�����ƂŁA�����I�Ƀf�[�^�̕������s���A�����S���̍������UKVS�ւƐi�����܂����B

  2. �������J�o���[�@�\
     1.�̋@�\���g�p���Ă���ꍇ�A���C���f�[�^�m�[�h���N���b�V�������ꍇ���A���C���f�[�^�m�[�h���A��A
     �X���[�u�m�[�h(���v���P�[�V�����m�[�h)���玩���I�Ƀf�[�^�𕜌����܂��B
     ���X���[�u�m�[�h���N���b�V�������ꍇ���A���A�㎩���I�Ƀ��C���f�[�^�m�[�h���畜������܂��B

  3. ��L2�̋@�\���g�p���Ă���ꍇ�̓m�[�h��~�����V�X�e���̒�~�Ȃ��Ɏg�p�\
     �f�[�^�m�[�h�N���b�V�������X���[�u�m�[�h(���v���P�[�V�����m�[�h)�ւ̎����ڍs���s���邽�߁A
     �g�p�V�X�e���̒�~������܂���B

  ����L�̎g�p���@�́Asrc\MasterNode.properties���Q�Ƃ��Ă��������B
  ��execMasterNode.bat�̓}�X�^�[�m�[�h���N�����܂��B
  ��execDataNode.bat�̓��C���f�[�^�m�[�h���N�����܂��B
  ��execSlaveDataNode.bat�̓X���[�u�f�[�^�m�[�h���N�����܂��B
========================================================================================================


�X�y�b�N
 ��������:Java(jdk1.6�ɂĊJ��)
 �\�[�X�G���R�[�f�B���O:UTF-8
 ���쌟��OS:WinsowsXp SP3�ACentOS 5.3(final)
 �K�v���C�u����:log4j-1.2.14.jar�Ajavamail-1.4.1.jar(JavaMail Ver1.4.1)
 Version:0.6.5(2010/05/30)


���@�\�����ƃT���v���̎��s���@
[�@�\����]
1.Key-Value�X�g�A
  Key-Value�X�g�A���������܂��B
  Key�͕�����AValue�͕�����ƁAbyte�f�[�^�̗�����o�^�\�ł��B

2.Tag�@�\
  Key�̑���Tag��o�^�ł��܂��B
  Tag�͕�����ƂȂ�܂��B
  �X�g�A�ł�Key�̓��j�[�N�Ȓl�Ƃ��Ĉ����܂����ATag�͕����̃f�[�^�ɕR�t����
  ���Ƃ��o���܂��B
  �����f�[�^�ɂ��炩���ߔC�ӂ�Tag��t���邱�ƂŁATag�w��ɂ��
  ��x�Ɋ֘A�f�[�^���擾�\�ƂȂ�܂��B
  �����݂�Tag�w��Ŋ֘A����f�[�^��Key�z�񂪎擾�ł��܂��B


4.�I���������ł���A�i�������ꂽ�f�[�^
  �f�[�^�̓o�^���N���C�A���g�����߂��A��������Ƃ��̃f�[�^��2��̃f�[�^�m�[�h�ɓo�^����܂�
  �o�^�̂��ꂩ���́AKey�l�̓�����(��1)�ƃt�@�C��(��2)�ɁAValue�l�̓t�@�C��(��3)�ɂ̂ݓo�^����܂��B
  Value�l��������(��1,4)�ɂ̂ݓo�^���邱�Ƃ��\�ł��B
  ��L2�ȊO�Ƀg�����U�N�V�������O�������Ƀt�@�C���ɓo�^���Ă��܂��B
  �f�[�^�m�[�h���_�E�����Ă��������ۑ����ꂽKey�l���t�@�C����񂩂畜�����邩�A
  Key�l�̃t�@�C���ւ̔��f�͒���I�ł��邽�߁A���̊Ԃŕۑ��O�Ƀ_�E�������������͔j�����Ă���ꍇ�́A
  �g�����U�N�V�������O���畜������܂��B

  ��1.�o�^�f�[�^�͊e�f�[�^�m�[�h���1��ConcurrentHashMap�Ɋi�[����܂��B
      �f�[�^�̓o�^�A���o���͑S�Ă�������s���܂��B
  ��2.�t�@�C���V�X�e���ɕۑ������f�[�^�́A����I�ɕۑ������ConcurrentHashMap��
      �V���A���C�Y�������f�[�^�ƁA�f�[�^�o�^���̃��O���ƂȂ�܂��B
      �V���A���C�Y�f�[�^�̓o�^�̓f�[�^�o�^�A�擾�����Ƃ͔񓯊��ɂĎ��s����܂��B
  ��3.Value�l�͌Œ蒷��LF���s��1�t�@�C���ɏ������܂�܂��B
      �L�^�����͒ǋL�^�ƂȂ�܂��B
      Key�l�͂���Value�l�̍ŐV�̈ʒu�������Ă��܂��B
  ��4.DataNode.properties��"KeyManagerJob1.dataMemory"�̒l�ŕύX�\
      true�Ń������ێ��Afalse�Ńt�@�C���ۑ�
      �ǂ���̏ꍇ���g�����U�N�V�������O�͕ۑ������̂ŁA�s���̃_�E���ɂ��f�[�^�̕����ɂ͉e���͂���܂���B
      ��KeyManagerJob1.memoryMode=true�̏ꍇ�͕�������܂���


5.���U�^
  �uokuyama�v�̓}�X�^�m�[�h�A�f�[�^�m�[�h�A�g�����U�N�V�����m�[�h�A�N���C�A���g��4�ō\������܂��B
  ���ꂼ��̖�ڂ͈ȉ��ł��B
  �}�X�^�m�[�h:�E�ݒ肳�ꂽ�A���S���Y��(��1)�ɏ]���āA�N���C�A���g����̃f�[�^����˗���K�؂�
                 �f�[�^�m�[�h�Ɉ˗����܂��B
               �E1�̃f�[�^��2��̃f�[�^�m�[�h�Ƀ��v���P�[�V�������܂�
                 �擾���ɊY���f�[�^�m�[�h���_�E�����Ă�ꍇ���A���v���P�[�V������̃f�[�^�m�[�h����擾���܂��B
                 �܂��A�f�[�^�m�[�h��2��Ƃ��ғ����Ă���ꍇ�́A�����𕪎U�����ו��U���s���܂��B
                 �f�[�^�o�^����1��̃f�[�^�m�[�h���_�E�����Ă���ꍇ���������̃m�[�h�ɕۑ��������𑱍s���܂��B
               �E������ł̏璷�����\�ł���B
                 ������ŉғ�����ꍇ�́AMasterNode���ł�Main�m�[�h�����肷��K�v������B
               �E��~�Ȃ��ł̓��I�ȃf�[�^�m�[�h�̒ǉ����������܂��B
                 �f�[�^�m�[�h��ǉ������ꍇ���A����܂łɓo�^�����f�[�^�ւ̃A�N�Z�X�͓����悤�ɉ\�ł��B
               �E���DataNode�̐������Ď����A�_�E������̕������Ƀf�[�^���ғ��m�[�h���玩�����J�o�[�����܂��B
                 ���J�o�[���̓f�[�^�̕s�������������Ȃ��悤�ɓ��������������܂��B
                 ��1.�Ǘ�����f�[�^�m�[�h�̐��Ɉˑ�����ȒP�ȃA���S���Y���ł��B
               �E�ݒ�t�@�C����src\MasterNode.properties


  �f�[�^�m�[�h:�E������ł̍\�����\
               �E�L�[�ƃf�[�^�̑g�ݍ��킹�Ńf�[�^��ۑ����܂��B
                 �f�[�^�̓o�^�A���o�A�폜�C���^�[�t�F�[�X�������܂��B
               �E���g�ł͑��m�[�h�ւ̃f�[�^�̐U�蕪���Ȃǂ͍s�Ȃ��܂���B
               �E�ݒ�t�@�C����src\DataNode.properties

  �g�����U�N�V�����m�[�h:�E���ULock(TransactionMode)���g�p����ꍇ��Lock��ێ��A�Ǘ����܂��B
                           TransactionMode���g�p���Ă��āA���̃m�[�h���_�E������Ƌɒ[�ɃX���[�v�b�g���_�E�����܂��B
                           ������C�\��B
                         �E�ݒ�t�@�C����src\TransactionNode.properties

  �N���C�A���g:�E�}�X�^�m�[�h�ւ̒ʐM���s�����ۂ̃v���O�����C���^�[�t�F�[�X�ł��B
	           �E�}�X�^�[�m�[�h�̏��𕡐��Z�b�g���邱�ƂŎ������U��A�}�X�^�[�m�[�h�_�E������
                 �ʃm�[�h�ւ̎����Đڑ��������Ȃ��܂��B
               �EJava��PHP���ꂼ��̃N���C���v���O����������܂��B
                 �g�p���@�͈ȉ��̍��������̓����[�X���̃T���v���v���O����TestSock.java�������́A
                 etc_clietn\PhpTestSock.php���Q�Ƃ��Ă��������B
                 �N���C�A���g�̃\�[�X�t�@�C����
                 Java��src\org\imdst\client\ImdstKeyValueClient.java
                 PHP��etc_client\OkuyamaClient.class.php

               �C���^�[�t�F�[�X�Ƃ��ẮA
               1.setValue(Key�l, Value�l)                 :[Key(������)��Value(������)�̑g�ݍ��킹�ł̃f�[�^�o�^]
               2.setValue(Key�l, Tag�l�z�� Value�l)       :[Key(������)��Tag(������(�z��))��Value(������)�̑g�ݍ��킹�ł̃f�[�^�o�^]
               3.getValue(Key�l)                          :[Key(������)�ł�Value(������)�擾]
               4.getTagKeys(Tag�l)                        :[Tag(������)�ł�Key�l�Q(Key�l�̔z��)�擾]
               5.setByteValue(Key�l, byte�l)              :[Key(������)��byte�z��̑g�ݍ��킹�ł̃f�[�^�o�^](PHP�͖�����)
               6.setByteValue(Key�l, Tag�l�z�� byte�l)    :[Key(������)��Tag(������(�z��))��byte�z��̑g�ݍ��킹�ł̃f�[�^�o�^](PHP�͖�����)
               7.getByteValue(Key�l)                      :[Key(������)��Value�l��Byte�z��Ŏ擾����.setByteValue�œo�^�����l�̂ݎ擾�ł���]
               8.removeValue(Key�l)                       :[Key(������)�Ńf�[�^���폜]
               9.getValueScript(Key�l,JavaScript�R�[�h)   :[Key(������)��JavaScript�R�[�h��n���A�擾���ꂽvalue�l��JavaScript�����s���l��Ԃ�]
              10.startTransaction()                       :[Transaction���[�h���̂݁BTransaction���J�n����(���ULock���g�p�\�ɂȂ�)]
              11.lockData(Key�l,Lock����,Lock�擾�҂�����):[Transaction���[�h���̂݁BKey�l��Lock���s���BLock���ԂŎw�肵�����Ԉێ������(0�͖�����)�A�ʂ̃N���C�A���g��Lock���Ă���ꍇ��Lock�擾�҂����Ԃ̊ԃ��g���C����]
              12.releaseLockData(Key�l)                   :[Transaction���[�h���̂݁B���g�̎擾����Lock���J������]
              13.setNewValue(Key�l, Value�l)              :[���o�^��Key�l�̏ꍇ�̂ݓo�^�ł���]
              14.setNewValue(Key�l, Tag�l�z�� Value�l)    :[���o�^��Key�l�̏ꍇ�̂ݓo�^�ł���]

  ���ꂼ��̃m�[�h�Ԃ̒ʐM��TCP/IP�ł̒ʐM�ƂȂ�܂��B
  �܂��A�N���C�A���g�ƃ}�X�^�m�[�h�Ԃ̒ʐM�͎����I��Base64�ɂăG���R�[�f�B���O������������g�p���Ă��܂��B



[�N�����@]
 ��Windows��

   �O�����:1.�\��
              1��̃}�V����ŉғ�����悤�ȃT���v���ݒ�t�@�C������������Ă��܂��B
              ���ꂼ��̃m�[�h�䐔
              �}�X�^�m�[�h:1��
              �f�[�^�m�[�h:2��(2�C���X�^���X�~2(�}�X�^�[�A�X���[�u))

            2.�e�m�[�h�̎g�p�|�[�g�͈ȉ��ƂȂ�܂��B
              �}�X�^�m�[�h:8888
              �p�r:�N���C�A���g����̗v���҂���
              �ύX����ꍇ:src�f�B���N�g���z����MasterNode.properties��7�s�ڂ�ύX
                           7�s��=MasterManagerJob.Init=8888<=���̔ԍ�

              �f�[�^�m�[�h:5553�A5554�@5556�A5557
              �p�r:�}�X�^�m�[�h����̗v���҂���
              �ύX����ꍇ:���C���f�[�^�m�[�h
                           src�f�B���N�g���z����DataNode.properties��7�s�ځA13�s�ڂ�ύX
                           7�s��=KeyManagerJob1.Init=5553<=���̔ԍ�
                           13�s��=KeyManagerJob2.Init=5554<=���̔ԍ�
                           �X���[�u�f�[�^�m�[�h
                           src�f�B���N�g���z����SlaveDataNode.properties��7�s�ځA13�s�ڂ�ύX
                           7�s��=KeyManagerJob1.Init=5556<=���̔ԍ�
                           13�s��=KeyManagerJob2.Init=5557<=���̔ԍ�

 1.�R���p�C��
   �ȈՓI�ȃR���p�C���p�o�b�`�t�@�C����p�ӂ��Ă��܂��B
   �{�t�@�C���Ɠ���f�B���N�g���ɂ���Acompile.bat�����s���Ă��������B
   �O��:javac.exe��PATH���ʂ��Ă���

 2.MasterNode�N��
   �ȈՓI��MasterNode�N���p�o�b�`�t�@�C����p�ӂ��Ă��܂��B
   �{�t�@�C���Ɠ���f�B���N�g���ɂ���AexecMasterNode.bat�����s���Ă��������B
   �ݒ�t�@�C����classes\MasterNode.properties���Q�Ƃ��Ă��܂��B
   ��~���@��Ctrl+C���v�����v�g�Ŏ��s
   ��ServerStop�t�@�C�������݂���ƃT�[�o�͋N�����܂���B
   ��execMasterNode2.bat�̓X���[�uMasterNode���N�����܂��B
   ��execMasterNodeMemcached.bat�̓X���[�uMasterNode��memcache�݊��v���g�R���ŋN�����܂��B
   �O��:1.java.exe��PATH���ʂ��Ă���
        2.�����������128MB�Ƃ��Ă��܂�

 3.DataNode�N��
   �ȈՓI��DataNode�N���p�o�b�`�t�@�C����p�ӂ��Ă��܂��B
   �{�t�@�C���Ɠ���f�B���N�g���ɂ���AexecDataNode.bat�����s���Ă��������B
   2�̃f�[�^�m�[�h�������ɋN�����܂��B
   �ݒ�t�@�C����classes\DataNode.properties���Q�Ƃ��Ă��܂��B
   ��~���@��Ctrl+C���v�����v�g�Ŏ��s
   ��ServerStop�t�@�C�������݂���ƃT�[�o�͋N�����܂���B
   �O��:1.java.exe��PATH���ʂ��Ă���
        2.�����������256MB�Ƃ��Ă��܂�

 3.SlaveDataNode�N��
   �ȈՓI�ȃX���[�u�pDataNode�N���p�o�b�`�t�@�C����p�ӂ��Ă��܂��B
   �{�t�@�C���Ɠ���f�B���N�g���ɂ���AexecSlaveDataNode.bat�����s���Ă��������B
   2�̃f�[�^�m�[�h�������ɋN�����܂��B
   �ݒ�t�@�C����classes\SlaveDataNode.properties���Q�Ƃ��Ă��܂��B
   ��~���@��Ctrl+C���v�����v�g�Ŏ��s
   ��ServerStop�t�@�C�������݂���ƃT�[�o��DataNode�͋N�����܂���B
   �O��:1.java.exe��PATH���ʂ��Ă���
        2.�����������256MB�Ƃ��Ă��܂�

 4.TransactionNode�N��
   �ȈՓI�ȕ��ULock�pTransactionNode�N���p�o�b�`�t�@�C����p�ӂ��Ă��܂��B
   �{�t�@�C���Ɠ���f�B���N�g���ɂ���AexecTransactionNode.bat�����s���Ă��������B
   �ݒ�t�@�C����classes\TransactionNode.properties���Q�Ƃ��Ă��܂��B
   ��~���@��Ctrl+C���v�����v�g�Ŏ��s
   ��ServerStop�t�@�C�������݂���ƃT�[�o�͋N�����܂���B
   �O��:1.java.exe��PATH���ʂ��Ă���
        2.�����������256MB�Ƃ��Ă��܂�

 ��execMasterNode2.bat�����s����ƁA�X���[�uMasterNode���N�����܂��B
   �|�[�g�ԍ���8889���g�p���܂��B
   execMasterNodeMemcached.bat�̓|�[�g11211�Ńv���g�R����memcache�ɂȂ�܂��B
   �ݒ�t�@�C����classes\MasterNode2.properties���Q�Ƃ��Ă��܂��B

   �E�N���T���v���ł̍\���}
                ����������������      ����������������
                �� �}�X�^�[   ��      �� �X���[�u   ��
                �� �m�[�h     ��      �� �}�X�^�[   ��
                �� Port:8888  ��      �� �m�[�h     ��
                ��            ��      �� Port:8889  ��
                ����������������      ����������������
                        ������������������������
            ������������������������
            ��                    ��
      ������������������ ������������������
      ������������������ ������������������
      �����f�[�^    ���� �����f�[�^    ����
      �����m�[�h    ���� �����m�[�h    ����
      ����Port:5553 ���� ����Port:5554 ����
      ������������������ ������������������
      ������������������ ������������������
      �����X���[�u  ���� �����X���[�u  ����
      �����f�[�^    ���� �����f�[�^    ����
      �����m�[�h    ���� �����m�[�h    ����
      ����Port:5556 ���� ����Port:5557 ����
      ������������������ ������������������
      ������������������ ������������������

 4.�T���v���̎��s���@
   �ȈՓI�Ȑڑ��A�o�^�A�擾�A�폜�T���v����p�ӂ��Ă��܂��B
   �{�t�@�C���Ɠ���f�B���N�g���ɂ���ATestSock.class�����s���Ă�������(jdk1.6�ɂăR���p�C���ς�)�B
   �����Ȃ��Ŏ��s����Ǝg�p���@���o�͂���܂��B
   ��)
     # �ȉ��̗�͎����I�ɃC���N�������g����Key�l��Value�������1000��o�^���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 1 127.0.0.1 8888 1000

     # �ȉ��̗�̓L�[�l��key_a�Ńo�����[�lvalue_b��o�^
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 1.1 127.0.0.1 key_a value_b

     # �ȉ��̗�͎����I�ɃC���N�������g����Key�l��Value�������1000��擾���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2 127.0.0.1 8888 1000

     # �ȉ��̗�̓L�[�l��key_a��value���擾
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2.1 127.0.0.1 8888 key_a

     # �ȉ��̗�̓}�X�^�[�m�[�hIP127.0.0.1,�|�[�g8888��IP127.0.0.1,�|�[�g8889�ɐڑ����o�����V���O����
     # �����I�ɃC���N�������g����Key�l��Value�������1000��擾���Ă���
     # execMasterNode2.bat���N�����Ă���ƁA����execMasterNode.bat�̃v���Z�X���I�����Ă��������ғ���������
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2.2 "127.0.0.1:8888,127.0.0.1:8889" 100

     # �ȉ��̗�̓L�[�l��key_a�Ŏ擾����Value�l��JavaScript�����s�����ʂ��擾
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 2.3 127.0.0.1 8888 key_a "var dataValue; var retValue = dataValue.replace('b', 'scritpChangeRet'); var execRet = '1';"

     # �ȉ��̗�͎����I�ɃC���N�������g����Key�l�ƓK����4�p�^�[����Tag�l��Value�������100��o�^���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 3 127.0.0.1 8888 100

     # �ȉ��̗��Tag�l�utag1�v�ɕR�t��Key�l��Value�l��1��擾���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 4 127.0.0.1 8888 1 tag1

     # �ȉ��̗��Key�l�uwordfile�v�ŁuC:\temp\SampleWord.doc�v�t�@�C����1��o�^���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 5 127.0.0.1 8888 1 C:\temp\SampleWord.doc wordfile

     # �ȉ��̗��Key�l�uwordfile�v�̃o�C�g�f�[�^���擾���uC:\SampleWord.doc�v�t�@�C���Ƃ���1��쐬���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 6 127.0.0.1 8888 1 C:\SampleWord.doc wordfile

     # �ȉ��̗��Key�l�ukey_a�v�̃f�[�^���폜���āAValue���擾���Ă���
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 8 127.0.0.1 8888 key_a

     # �ȉ��̗��Transaction���J�n���ăf�[�^��Lock��A�f�[�^���X�V�A�擾���ALock������
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 10 127.0.0.1 8888 key_a 5 10

     # �ȉ��̗��1�x�����f�[�^��o�^����ꍇ�Ɏg�p����Ăяo��
     # "key_abc"�Ƃ���Key��1�x�����o�^���Ȃ��悤�ɂ������ꍇ
     # 2�x���s�����2��ڂ̓G���[�ƂȂ�B(memcache��add�ɑ�������)
     java -cp ./;./classes;./lib/javamail-1.4.1.jar TestSock 11 127.0.0.1 8888 key_abc value_abc

     PHP�Ɋւ��ẮAetc_client\PhpAutoTest.bat���Q�Ƃ��Ă��������B

[����]
 ����̓o�OFix�ƕ��U�g�����U�N�V����(���b�N�@�\)���������Ă����܂��B

