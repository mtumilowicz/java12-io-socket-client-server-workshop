package answers

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step3_ClientConnectionAnswerTest extends Specification {
    def "draft"() {
        given:
        PipedOutputStream oStream = new PipedOutputStream()
        PipedInputStream iStream = new PipedInputStream(oStream)
        
        def socket = Mock(Socket) {
            getOutputStream() >> oStream
            getInputStream() >> iStream
        }
        
        when:
        oStream.write("XXX ".bytes)
        new Step3_ClientConnectionAnswer(socket).run()
        
        then:
        iStream.newReader().readLine() == "Hello, XXX What's you name?"
    }
}
