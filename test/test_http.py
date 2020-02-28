def test_get_content_from_http(host):
    output = host.check_output('wget -qO- 0.0.0.0:8080')
    assert 'Hello World!' in output

def test_host_listens_on_port_8080(host):
    assert host.socket("tcp://0.0.0.0:8080").is_listening