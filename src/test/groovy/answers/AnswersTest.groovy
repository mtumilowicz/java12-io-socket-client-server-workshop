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
//
//    def "Step4_SingleThreadedServerAnswer"() {
//        expect:
//        expectedClientOutput == extractClientOutputFor(new Step4_SingleThreadedServerAnswer())
//    }
//
//    def "Step5_ThreadPerRequestServerAnswer"() {
//        expect:
//        expectedClientOutput == extractClientOutputFor(new Step5_ThreadPerRequestServerAnswer())
//    }
//
//    def "Step7_SingleThreadedServerAnswer"() {
//        expect:
//        expectedClientOutput == extractClientOutputFor(new Step7_SingleThreadedServerAnswer())
//    }
//
//    def "Step8_ThreadPerRequestServerAnswer"() {
//        expect:
//        expectedClientOutput == extractClientOutputFor(new Step8_ThreadPerRequestServerAnswer())
//    }
//
//    def "Step9_ThreadPoolServerAnswer"() {
//        expect:
//        expectedClientOutput == extractClientOutputFor(new Step9_ThreadPoolServerAnswer())
//    }

    def extractClientOutputFor(port, server) {
        def t = new Thread({ server.start() })
        t.start()
        Thread.sleep(10)
        def output = new TestClient(port).run()
        t.interrupt()
        
        output
    }
}