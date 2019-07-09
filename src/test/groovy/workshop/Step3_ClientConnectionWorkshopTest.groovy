package workshop


import spock.lang.Specification 
/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step3_ClientConnectionWorkshopTest extends Specification {
    def "test communication"() {
        given:
        def outputStream = new ByteArrayOutputStream()
        def inputStream = new ByteArrayInputStream("Michal".bytes)

        def socket = Mock(Socket) {
            getOutputStream() >> outputStream
            getInputStream() >> inputStream
        }

        when:
        new Step3_ClientConnectionWorkshop(socket).run()

        then:
        outputStream.toString() == "What's your name?\r\nHello, Michal\r\n"
    }
}
