package answers

import client.TestClient
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step1_SingleThreadedServerAnswerTest extends Specification {
    
    def server = new Step1_SingleThreadedServerAnswer()

    def "check communication with test client"() {
        when:
        new Thread({ server.start() }).start()
        Thread.sleep(10)
        def output = new TestClient().run()

        then:
        output == ["received: What's you name?", "send: Michal", "received: Hello, Michal"]
    }
}
