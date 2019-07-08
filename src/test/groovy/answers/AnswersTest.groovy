package answers

import client.TestClient
import spock.lang.Specification


/**
 * Created by mtumilowicz on 2019-07-08.
 */
class AnswersTest extends Specification {

    def expectedClientOutput = ["received: What's you name?", "send: Michal", "received: Hello, Michal"]

    def "Step1_SingleThreadedServerAnswer"() {
        given:
        def port = 1
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step1_SingleThreadedServerAnswer(port))
    }

    def "Step2_ThreadPerRequestServerAnswer"() {
        given:
        def port = 2

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step2_ThreadPerRequestServerAnswer(port))
    }

    def "Step4_SingleThreadedServerAnswer"() {
        given:
        def port = 3
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step4_SingleThreadedServerAnswer(port))
    }

    def "Step5_ThreadPerRequestServerAnswer"() {
        given:
        def port = 5

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step5_ThreadPerRequestServerAnswer(port))
    }

    def "Step7_SingleThreadedServerAnswer"() {
        given:
        def port = 7

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step7_SingleThreadedServerAnswer(port))
    }

    def "Step8_ThreadPerRequestServerAnswer"() {
        given:
        def port = 8

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step8_ThreadPerRequestServerAnswer(port))
    }

    def "Step9_ThreadPoolServerAnswer"() {
        given:
        def port = 9

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step9_ThreadPoolServerAnswer(port))
    }

    def extractClientOutputFor(port, server) {
        def t = new Thread({ server.start() })
        t.start()
        Thread.sleep(10)
        def output = new TestClient(port).run()
        t.interrupt()
        
        output
    }
}