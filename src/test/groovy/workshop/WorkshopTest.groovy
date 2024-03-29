package workshop

import client.TestClient
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2019-07-08.
 */
class WorkshopTest extends Specification {

    def expectedClientOutput = ["received: What's your name?", "send: Michal", "received: Hello, Michal"]

    def "Step1_SingleThreadedServerWorkshop"() {
        given:
        def port = 10
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step1_SingleThreadedServerWorkshop(port))
    }

    def "Step2_ThreadPerConnectionServerWorkshop"() {
        given:
        def port = 11

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step2_ThreadPerConnectionServerWorkshop(port))
    }

    def "Step4_SingleThreadedServerWorkshop"() {
        given:
        def port = 12
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step4_SingleThreadedServerWorkshop(port))
    }

    def "Step5_ThreadPerConnectionServerWorkshop"() {
        given:
        def port = 13

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step5_ThreadPerConnectionServerWorkshop(port))
    }

    def "Step7_SingleThreadedServerWorkshop"() {
        given:
        def port = 14

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step7_SingleThreadedServerWorkshop(port))
    }

    def "Step8_ThreadPerConnectionServerWorkshop"() {
        given:
        def port = 15

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step8_ThreadPerConnectionServerWorkshop(port))
    }

    def "Step9_ThreadPoolServerWorkshop"() {
        given:
        def port = 16

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step9_ThreadPoolServerWorkshop(port))
    }

    def extractClientOutputFor(port, server) {
        new Thread({ server.start() }).start()
        Thread.sleep(10)
        new TestClient(port).run()
    }
}