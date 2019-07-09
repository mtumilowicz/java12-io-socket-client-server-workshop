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
        def port = 1
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step1_SingleThreadedServerWorkshop(port))
    }

    def "Step2_ThreadPerRequestServerWorkshop"() {
        given:
        def port = 2

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step2_ThreadPerRequestServerWorkshop(port))
    }

    def "Step4_SingleThreadedServerWorkshop"() {
        given:
        def port = 3
        
        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step4_SingleThreadedServerWorkshop(port))
    }

    def "Step5_ThreadPerRequestServerWorkshop"() {
        given:
        def port = 5

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step5_ThreadPerRequestServerWorkshop(port))
    }

    def "Step7_SingleThreadedServerWorkshop"() {
        given:
        def port = 7

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step7_SingleThreadedServerWorkshop(port))
    }

    def "Step8_ThreadPerRequestServerWorkshop"() {
        given:
        def port = 8

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step8_ThreadPerRequestServerWorkshop(port))
    }

    def "Step9_ThreadPoolServerWorkshop"() {
        given:
        def port = 9

        expect:
        expectedClientOutput == extractClientOutputFor(port, new Step9_ThreadPoolServerWorkshop(port))
    }

    def extractClientOutputFor(port, server) {
        new Thread({ server.start() }).start()
        Thread.sleep(10)
        new TestClient(port).run()
    }
}