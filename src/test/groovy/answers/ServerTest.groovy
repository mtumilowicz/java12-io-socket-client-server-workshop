package answers

import client.TestClient
import spock.lang.Specification


/**
 * Created by mtumilowicz on 2019-07-08.
 */
abstract class ServerTest extends Specification {
    
    def "check communication with test client"() {
        when:
        new Thread({ getServer().start() }).start()
        Thread.sleep(10)
        def output = new TestClient().run()

        then:
        output == ["received: What's you name?", "send: Michal", "received: Hello, Michal"]
    }
    
    abstract def getServer()
}