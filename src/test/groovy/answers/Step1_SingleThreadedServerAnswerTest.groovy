package answers

import client.TestClient
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step1_SingleThreadedServerAnswerTest extends Specification {

    def "check communication with test client"() {
        when:
        new Thread({ new Step1_SingleThreadedServerAnswer().start() }).start()
        Thread.sleep(100)
        def output = new TestClient().run()

        then:
        output == ["received: What's you name?", "send: Michal", "received: Hello, Michal"]
    }
}
