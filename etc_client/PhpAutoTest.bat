rem �L�[�l�������ŃC���N�������g����10�o�^
php PhpTestSock.php 1 127.0.0.1 8888 10
rem �L�[�l��key_a�Ńo�����[�lvalue_b��o�^
php PhpTestSock.php 1.1 127.0.0.1 8888 key_a value_b
rem �L�[�l�������ŃC���N�������g����10��value���擾
php PhpTestSock.php 2 127.0.0.1 8888 10
rem �L�[�l��key_a��value���擾
php PhpTestSock.php 2.1 127.0.0.1 8888 key_a
rem �L�[�l��key_a�Ŏ擾����value�ɑ΂���JavaScript�����s
php PhpTestSock.php 2.3 127.0.0.1 8888 key_a "var dataValue; var retValue = dataValue.replace('b', 'dummy'); var execRet = '1';"
rem �L�[�l��key_a�Ŏ擾����value�ɑ΂���JavaScript�����s
php PhpTestSock.php 2.4 127.0.0.1 8888 key_a "var dataValue; var dataKey; var retValue = dataValue.replace('b', 'dummy'); if(dataKey == 'key_a') {var execRet = '2'} else {var execRet = '1'}"
rem Tag�l�������ŕς��āAKey��Value��10��o�^
php PhpTestSock.php 3 127.0.0.1 8888 10
rem Tag�l��tag1���w�肵�āAtag1�ɑ�����Key�l���擾(Key�l���ݎw��L��(true))
php PhpTestSock.php 4 127.0.0.1 8888 tag1 true
rem Tag�l��tag1���w�肵�āAtag1�ɑ�����Key�l���擾(Key�l���ݎw��L��(false))
php PhpTestSock.php 4 127.0.0.1 8888 tag1 false
rem �L�[�l��key_a��Value���폜
php PhpTestSock.php 8 127.0.0.1 8888 key_a
rem ���U���b�N���g�p����
php PhpTestSock.php 9 127.0.0.1 8888 key_a 10 5
rem �l�̐V�K�o�^�������Ȃ�
php PhpTestSock.php 10 127.0.0.1 8888 newkey newvalue
rem gets
php PhpTestSock.php 11 127.0.0.1 8888 newkey
rem cas
php PhpTestSock.php 12 127.0.0.1 8888 newkey value_cas 0
rem cas Miss
php PhpTestSock.php 12 127.0.0.1 8888 newkey value_cas 1
rem cas Tag
php PhpTestSock.php 13 127.0.0.1 8888 newkey value_cas tag1 2
rem gets
php PhpTestSock.php 11 127.0.0.1 8888 newkey
