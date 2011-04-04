<?php

  // PHP�e�X�g�X�N���v�g
  // PHP�ł̃f�[�^�o�^
  // ������
  // 1.���s���[�h:"1" or "1.1" ro "2" or "2.1" or "2.3" or "3" or "4" or "8"
  // (1=�o�^(�����C���N�������g), 1.1=�o�^(�l�w��), 2=�f�[�^�擾(�����C���N�������g), 2.1=�擾(�l�w��), 2.3=�擾(JavaScript���s), 3=Tag�Œl��Key��Value��o�^(�����C���N�������g), 4=Tag�Œl��Key���擾(Tag�l�w��), 8=Key�l���w��ō폜))
  // 2.�}�X�^�[�m�[�hIP:127.0.0.1
  // 3.�}�X�^�[�m�[�hPort:8888
  // 4.���s��:0�`n(���s���[�h1.1�y��2.1�y��2.3�y��3�y��8���͓o�^�A�擾�A�폜������Key or Tag�l)
  // 5.�o�^�f�[�^:(���s���[�h1.1�y��2.3���̂ݗL�� 1.1���͓o�^������Value�l�A2.3���͎��s������JavaScript)

    require_once("OkuyamaClient.class.php");

  if ($argc > 3) {

    // �N���C�A���g�쐬
    $client = new OkuyamaClient();

    // �ڑ�(MasterServer�Œ�)
    if(!$client->connect($argv[2], $argv[3])) {
      print_r("Sever Connection refused !!");
      exit;
    }

/*
    // AutoConnect���[�h�Őڑ�
    $serverInfos = array();
    $serverInfos[0] = "localhost:8888";
    $serverInfos[1] = "localhost:8889";
    // ����MasterServer�̏���ݒ�
    $client->setConnectionInfos($serverInfos);

    // �����ڑ�
    if(!$client->autoConnect()) {
      // �S�Ă̌��̃T�[�o�ɂȂ���Ȃ�
      print_r("Sever Connection refused !!");
      exit;
    }
*/
    // ����
    if ($argv[1] === "1") {

      // �f�[�^�������̉񐔕��o�^
      for ($i = 0; $i < $argv[4]; $i++) {
        
        if(!$client->setValue("datasavekey_" . $i, "savedatavaluestr_" . $i)) {
          print_r("Registration failure");
        }
      }
    } else if ($argv[1] === "1.1") {

      // �f�[�^�������̉񐔕��o�^
      if(!$client->setValue($argv[4], $argv[5])) {
        print_r("Regist Error");
      }

    } else if ($argv[1] === "42") {

      // �f�[�^�������̉񐔕��o�^
      for ($i = 0; $i < $argv[4]; $i++) {
        
        if(!$client->setValueAndCreateIndex("datasavekey_" . $i, "savedatavaluestr_" . $i)) {
          print_r("Registration failure");
        }
      }
    } else if ($argv[1] === "42.1") {

      // �f�[�^�������̉񐔕��o�^
      for ($i = 0; $i < $argv[4]; $i++) {
        
        if(!$client->setValueAndCreateIndex("datasavekey_" . $argv[5] . "_" . $i, "savedatavaluestr_" . $argv[5] . "_" . $i, null, $argv[5])) {
          print_r("Registration failure");
        }
      }
    } else if ($argv[1] === "43") {


      // �f�[�^������Word�Ŏ擾(����Word1�AAND,OR�APrefix�w��)
      $searchWordList = array();
      $searchWordList[0] = $argv[4];

      var_dump($client->searchValue($searchWordList, $argv[5], $argv[6]));
    } else if ($argv[1] === "43.1") {


      // �f�[�^������Word�Ŏ擾(����Word2�AAND,OR�APrefix�w��)
      $searchWordList = array();
      $searchWordList[0] = $argv[4];
      $searchWordList[1] = $argv[5];
      var_dump($client->searchValue($searchWordList, $argv[6], $argv[7]));
    } else if ($argv[1] === "43.2") {


      // �f�[�^������Word�Ŏ擾(����Word2�AAND,OR�w��)
      $searchWordList = array();
      $searchWordList[0] = $argv[4];
      $searchWordList[1] = $argv[5];
      var_dump($client->searchValue($searchWordList, $argv[6]));
    } else if ($argv[1] === "2") {

      // �f�[�^�������̉񐔕��擾
      for ($i = 0; $i < $argv[4]; $i++) {
        $ret = $client->getValue("datasavekey_" . $i);
        if ($ret[0] === "true") {
          print_r($ret[1]);
          print_r("\r\n");
        } else {
          print_r("There is no data");
          print_r("\r\n");
        }
      }
    } else if ($argv[1] === "2.1") {
      // �w���Key�l�Ńf�[�^���擾

      $ret = $client->getValue($argv[4]);
      if ($ret[0] === "true") {
        print_r($ret[1]);
        print_r("\r\n");
      } else if ($ret[0] === "false") {
        print_r("There is no data");
        print_r("\r\n");
      }
    } else if ($argv[1] === "2.3") {
      // �w���Key�l�Ńf�[�^���擾

      $ret = $client->getValueScript($argv[4], $argv[5]);
      if ($ret[0] === "true") {
        print_r($ret[1]);
        print_r("\r\n");
      } else if ($ret[0] === "false") {
        print_r("There is no data");
        print_r("\r\n");
      }
    } else if ($argv[1] === "2.4") {
      // �w���Key�l�Ńf�[�^���擾

      $ret = $client->getValueScriptForUpdate($argv[4], $argv[5]);
      if ($ret[0] === "true") {
        print_r($ret[1]);
        print_r("\r\n");
      } else if ($ret[0] === "false") {
        print_r("There is no data");
        print_r("\r\n");
      }
    } else if ($argv[1] === "3") {

      // �f�[�^�������̉񐔕��o�^(Tag��o�^)
      $counter = 0;
      for ($i = 0; $i < $argv[4]; $i++) {
        $tags = array();
        if ($counter === 0) {
          $tags[0] = "tag1";
          $counter++;
        } else if($counter === 1) {
          $tags[0] = "tag1";
          $tags[1] = "tag2";
          $counter++;
        } else if($counter === 2) {
          $tags[0] = "tag1";
          $tags[1] = "tag2";
          $tags[2] = "tag3";
          $counter++;
        } else if($counter === 3) {
          $tags[0] = "tag4";
          $counter++;
        } else if($counter === 4) {
          $tags[0] = "tag4";
          $tags[1] = "tag2";
          $counter = 0;
        }
        if(!$client->setValue("datasavekey_" . $i, "savedatavaluestr_" . $i, $tags)) {
          print_r("Registration failure");
        }
      }
    } else if ($argv[1] === "4") {

      // �f�[�^�������̉񐔕��擾(Tag�Ŏ擾)
      $counter = 0;
      if ($argv[5] === "true") {
        var_dump($client->getTagKeys($argv[4], true));
      } else if($argv[5] === "false") {
        var_dump($client->getTagKeys($argv[4], false));
      } else {
        var_dump($client->getTagKeys($argv[4]));
      }


    } else if ($argv[1] === "7") {

      // �f�[�^�������̉񐔕��擾
      for ($i = 0; $i < $argv[4]; $i++) {
        $ret = $client->removeValue("datasavekey_" . $i);
        if ($ret[0] === "true") {
          // �폜����
          print_r($ret[1]);
          print_r("\r\n");
        } else if ($ret[0] === "false") {
          // Key�l�Ńf�[�^�Ȃ�
          print_r("There is no data");
          print_r("\r\n");
        } else if ($ret[0] === "error") {
          // �폜�����ŃG���[
          print_r($ret[1]);
          print_r("\r\n");
        }
      }
    } else if ($argv[1] === "8") {

      // ������Key�l�̃f�[�^���폜
      $ret = $client->removeValue($argv[4]);
      if ($ret[0] === "true") {
        // �폜����
        print_r($ret[1]);
        print_r("\r\n");
      } else if ($ret[0] === "false") {
        // Key�l�Ńf�[�^�Ȃ�
        print_r("There is no data");
        print_r("\r\n");
      } else if ($ret[0] === "error") {
        // �폜�����ŃG���[
        print_r($ret[1]);
        print_r("\r\n");
      }
    } else if ($argv[1] === "9") {
      if(!$client->startTransaction()) {

        print_r("Transaction Start Error !!");
        exit;
      }
      // ������Key�l�̃f�[�^���폜

      $ret = $client->lockData($argv[4], $argv[5], $argv[6]);
      if ($ret[0] === "true") {
        // Lock����
        print_r("Lock����");
        print_r("\r\n");
      } else if ($ret[0] === "false") {
        // Key�l�Ńf�[�^�Ȃ�
        print_r("Lock���s");
        print_r("\r\n");
        } else if ($ret[0] === "error") {
        // �폜�����ŃG���[
        print_r("Lock Error");
        print_r("\r\n");
      }

      // ���g�Ń��b�N���Ă���̂ōX�V�\
      if(!$client->setValue($argv[4], "LockDataPhp")) {
        print_r("Registration failure");
      }

      $ret = $client->getValue($argv[4]);
      if ($ret[0] === "true") {
        print_r($ret[1]);
        print_r("\r\n");
      } else {
        print_r("There is no data");
        print_r("\r\n");
      }

      // ���g�Ń��b�N���Ă���̂ō폜�\
      $ret = $client->removeValue($argv[4]);
      if ($ret[0] === "true") {
        // �폜����
        print_r("�폜 [" . $ret[1] . "]");
        print_r("\r\n");
      } else if ($ret[0] === "false") {
        // Key�l�Ńf�[�^�Ȃ�
        print_r("There is no data");
        print_r("\r\n");
      } else if ($ret[0] === "error") {
        // �폜�����ŃG���[
        print_r($ret[1]);
        print_r("\r\n");
      }

      $ret = $client->releaseLockData($argv[4]);
      var_dump($ret);

    } else if ($argv[1] === "10") {
      // �f�[�^�������̉񐔕��o�^
      var_dump($client->setNewValue($argv[4], $argv[5]));

    } else if ($argv[1] === "11") {
      // gets
      var_dump($client->getValueVersionCheck($argv[4]));

    } else if ($argv[1] === "12") {
      // cas
      var_dump($client->setValueVersionCheck($argv[4], $argv[5], null, $argv[6]));

    } else if ($argv[1] === "13") {
      // cas tag�t
      var_dump($client->setValueVersionCheck($argv[4], $argv[5], $argv[6], $argv[7]));

    } else if ($argv[1] === "20") {

      // incr
      var_dump($client->incrValue($argv[4], $argv[5]));
    } else if ($argv[1] === "21") {

      // decr
      var_dump($client->decrValue($argv[4], $argv[5]));
    } else if ($argv[1] === "22") {

      // Tag�폜
      var_dump($client->removeTagFromKey($argv[4], $argv[5]));
    }




    $client->close();
  } else {
    print_r("Args Error!!");
  }
?>