okuyamaFuse��okuyama�̓X�g���[�W�Ƃ��ė��p����t�@�C���V�X�e���ł��B
Linux�p�̃t�@�C���V�X�e���Ƃ��Ď�������Ă���ACentOS5.8��ŊJ�����܂����B
�x�[�^�łȂ̂ŁA�e�X�g�p�Ƃł��g�p���������B
����ł̓}�E���g�f�B���N�g����11TB�Ɍ����܂��B�f�B���N�g���S�̗̂e�ʂ͑������܂���B


[�d�g��]
 Fuse�x�[�X�Ńt�@�C���V�X�e�����������Ă��܂��B
 ���̂��߁AFuse�ɑΉ�����OS�ł̂݉ғ��\�ł��B
 ����m�F��CentOS5.8�A6.0�ł̂ݎ��{���Ă��܂��B


[�ˑ�]
 Linux�J�[�l�����W���[���ł���Fuse�Ɉˑ����܂��B
 �܂��AFuse��Java�o�C���f�B���O�ł���FUSE-J�𗘗p���Ă��܂��B
 
   FUSE-J
    http://sourceforge.net/projects/fuse-j/
   Version
    2.4

 okuyama�𗘗p���Ă��邽�߁Aokuyama�̎��s�����K�v�ł��B
   Version-0.9.4�ȏ�
   ��DataNode�͂ǂ̃X�g���[�W���[�h�ł������܂��B
     �uDataSaveMapType=serialize�v�Ƃ��Ĉ��k�������𗘗p����ꍇ�́A
     �uSerializerClassName=�v�Ɂuokuyama.imdst.util.serializemap.ByteDataMemoryStoreSerializer�v��
      �ݒ肷�邱�Ƃ𐄏����܂��B���k���A���\���ɁuObjectStreamSerializer�v�����D��Ă��܂��B


[���p���@](Java��Ant�͑S�ăZ�b�g�A�b�v�ς݂Ƃ��܂��Bokuyama��192.168.1.1��192.168.1.2�T�[�o��8888�Ԃ̃|�[�g�ŋN�����Ă�����̂Ƃ��܂�)
 1.FUSE���Z�b�g�A�b�v���܂��B
   $yum install fuse* 
   (devel�����S�ăC���X�g�[�����Ă�������)
   ���u$modprobe fuse�v���s���G���[���o�Ȃ����Ƃ��m�F

 2.FUSE-J���Z�b�g�A�b�v
   ���_�E�����[�h�y�єz�u
   $wget http://jaist.dl.sourceforge.net/project/fuse-j/fuse-j/FUSE-J%202.4%20prerelease1/fuse-j-2.4-prerelease1.tar.gz
   $tar -zxvf fuse-j-2.4-prerelease1.tar.gz


   ��JNI���Z�b�g�A�b�v
    ���R���p�C���O��build.conf�́uJDK_HOME=/opt/jdk1.5.0�v�����������`�F�b�N
   $cd fuse-j-2.4-prerelease1
   $mkdir build
   $make
    ��jni�f�B���N�g���̔z����libjavafs.so���쐬����Ă���ΐ����ł��B

   ��FUSE-J���Z�b�g�A�b�v
   $ant compile
   $ant dist
    ��dist�f�B���N�g���̔z����fuse-j.jar���쐬����Ă���ΐ����ł��B

   �����s����p��
    libjavafs.so�Afuse-j.jar�Aokuyama-{version}.jar�AokuyamaFuse-{version}.jar�Aokuyama/lib/javamail-1.4.1.jar�Aokuyama/etc_client/okuyamaFuse/lib/fuse-j-2.4/lib/commons-logging-1.0.4.jar��
    ��L�̃t�@�C����S�ēK����1�f�B���N�g���ɔz�u

   ���}�E���g�f�B���N�g�����쐬
   $mkdir /var/tmp/okuyamafuse

   ���}�E���g
     ���ȉ���Mount���s(�ŏI�s��MasterNode��IP�ƃ|�[�g�ԍ���":"�ŘA�����A","�Ōq���ŗ񋓂���(���̏ꍇ1�����L�q)
      ��/usr/local/lib�z����FUSE�̃��C�u�������z�u����Ă���z��
   LD_LIBRARY_PATH=./:/usr/local/lib java -classpath \
    ./okuyamaFuse-0.0.1.jar:./fuse-j.jar:./commons-logging-1.0.4.jar:./okuyama-0.9.4.jar:./javamail-1.4.1.jar \
    -Dorg.apache.commons.logging.Log=fuse.logging.FuseLog \
    -Dfuse.logging.level=ERROR -Xmx1024m -Xms1024m -server \
    -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseParNewGC \
    fuse.okuyamafs.OkuyamaFuse \
    -f -o allow_other \
    /var/tmp/okuyamafuse \
    192.168.1.1:8888,192.168.1.2:8888

   ���A���}�E���g
   $fusermount -u /var/tmp/okufs
   $kill -9 ���s�v���Z�X



