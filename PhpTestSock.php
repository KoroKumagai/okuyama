<?php
    
    $host = "127.0.0.1";
    $port = "8888";
    $sock = fsockopen($host, $port);

    // Set
    $key =  base64_encode("datasavekey_php_0");
    $value = base64_encode("datasavedata_php_0");

    // Value��Base64�ŃG���R�[�h
    value = new String(BASE64EncoderStream.encode(value.getBytes()));
            }


            // ������o�b�t�@������
            serverRequestBuf = new StringBuffer();


            // �����ԍ��A��
            serverRequestBuf.append("1");
            // �Z�p���[�^�A��
            serverRequestBuf.append(ImdstKeyValueClient.sepStr);


            // Key�A��(Key�̓f�[�^���M���ɂ͕K�������񂪕K�v)
            serverRequestBuf.append(new String(BASE64EncoderStream.encode(keyStr.getBytes())));
            // �Z�p���[�^�A��
            serverRequestBuf.append(ImdstKeyValueClient.sepStr);


            // Tag�A��
            // Tag�w��̗L���𒲂ׂ�
            if (tagStrs == null || tagStrs.length < 1) {

                // �u�����N�K�蕶�����A��
                serverRequestBuf.append(ImdstKeyValueClient.blankStr);
            } else {

                // Tag�����A��
                serverRequestBuf.append(new String(BASE64EncoderStream.encode(tagStrs[0].getBytes())));
                for (int i = 1; i < tagStrs.length; i++) {
                    serverRequestBuf.append(tagKeySep);
                    serverRequestBuf.append(new String(BASE64EncoderStream.encode(tagStrs[i].getBytes())));
                }
            }

            // �Z�p���[�^�A��
            serverRequestBuf.append(ImdstKeyValueClient.sepStr);

            // Value�A��
            serverRequestBuf.append(value);

            // �T�[�o���M
            pw.println(serverRequestBuf.toString());
            pw.flush();









    // Get
    $key =  base64_encode("datasavekey_php_0");

    $request =  "2#imdst3674#" . $key . "\r\n";
    if(!$sock){

        $data = 'socket error�F' . $host;
    }else{

        fputs($sock, $request);

        $work = fgets($sock);
        $work = str_replace("\r\n", "", $work);
        $strs = explode("#imdst3674#", $work);

        var_dump(base64_decode($strs[2]));
        fclose($sock);
    }

?>