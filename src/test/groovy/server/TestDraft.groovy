package server

import client.Client
import server.SingleThreadedServer
import spock.lang.Specification


/**
 * Created by mtumilowicz on 2019-07-07.
 */
class TestDraft extends Specification {

    def "xx1"() {
        new Thread( { new SingleThreadedServer().start() }).start()
        Thread.sleep(1000)
        new Client().run()
        
        expect:
        1 == 1
    }
}