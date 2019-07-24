package answers

import client.TestClient
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2019-07-08.
 */
class AnswersTest extends Specification {

    def expectedClientOutput = ["received: What's your name?", "send: Michal", "received: Hello, Michal"]

    def "Step1_SingleThreadedServerAnswer"() {
        given:
        def port = 1
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step1_SingleThreadedServerAnswer(port))
    }

    def "Step2_ThreadPerConnectionServerAnswer"() {
        given:
        def port = 2

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step2_ThreadPerConnectionServerAnswer(port))
    }

    def "Step4_SingleThreadedServerAnswer"() {
        given:
        def port = 3
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step4_SingleThreadedServerAnswer(port))
    }

    def "Step5_ThreadPerConnectionServerAnswer"() {
        given:
        def port = 5

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step5_ThreadPerConnectionServerAnswer(port))
    }

    def "Step7_SingleThreadedServerAnswer"() {
        given:
        def port = 7

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step7_SingleThreadedServerAnswer(port))
    }

    def "Step8_ThreadPerConnectionServerAnswer"() {
        given:
        def port = 8

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step8_ThreadPerConnectionServerAnswer(port))
    }

    def "Step9_ThreadPoolServerAnswer"() {
        given:
        def port = 9

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step9_ThreadPoolServerAnswer(port))
    }

    def extractClientOutputFor(port, server) {
        new Thread({ server.start() }).start()
        Thread.sleep(10)
        new TestClient(port).run()
    }
}