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
rem Tag�l�������ŕς��āAKey��Value��10��o�^
php PhpTestSock.php 3 127.0.0.1 8888 10
rem Tag�l��tag1���w�肵�āAtag1�ɑ�����Key�l���擾
php PhpTestSock.php 4 127.0.0.1 8888 tag1
rem �L�[�l��key_a��Value���폜
php PhpTestSock.php 8 127.0.0.1 8888 key_a
rem ���U���b�N���g�p����
php PhpTestSock.php 9 127.0.0.1 8888 key_a 10 5