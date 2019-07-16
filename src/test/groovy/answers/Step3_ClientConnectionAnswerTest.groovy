package answers

import spock.lang.Specification 
/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step3_ClientConnectionAnswerTest extends Specification {
    def "test communication"() {
        given:
        def outputStream = new ByteArrayOutputStream()
        def inputStream = new ByteArrayInputStream("Michal".bytes)

        def socket = Mock(Socket) {
            getOutputStream() >> outputStream
            getInputStream() >> inputStream
        }

        when:
        new Step3_ClientConnectionAnswer(socket).run()

        then:
        outputStream.toString().contains('Hello, Michal')
    }
}
