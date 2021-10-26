package mx.tecnm.tepic.ladm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var game:newplay ?= null

        btn.setOnClickListener {

            //Desaparece boton
            btn.visibility = View.INVISIBLE
            reseteo()

            game = newplay(this)
            game!!.start()
        }
    }

    fun reseteo(){
        reg1.text = "Registro :"
        total1.text = "Puntaje:"
        reg2.text = "Registro :"
        total2.text = "Puntaje:"
        txtganador.text = ""
    }
}

class newplay(p:MainActivity) : Thread(){
    var principal = p
    var registro1 = "Registro :"
    var registro2 = "Registro :"
    val player1 = Player()
    val player2 = Player()

    override fun run() {
        super.run()

        //Juega Player 1
        player1.start()
        sleep(1000)
        player1.pausa()
        registro1 += "Turno ${player1.numTiro} (${player1.dado})"
        principal.runOnUiThread{
            principal.reg1.text = registro1
            principal.total1.text = "Total: ${player1.total}"
        }

        sleep(1500)

        //Juega Player 2
        player2.start()
        sleep(1000)
        player2.pausa()
        registro2 += "Turno ${player2.numTiro} (${player2.dado})"
        principal.runOnUiThread{
            principal.reg2.text = registro2
            principal.total2.text = "Total: ${player2.total}"
        }

        sleep(1500)

        //Vuelve a jugar Player 1
        player1.pausa()
        sleep(2000)
        player1.pausa()
        registro1 += "Turno ${player1.numTiro} (${player1.dado})"
        principal.runOnUiThread{
            principal.reg1.text = registro1
            principal.total1.text = "Total: ${player1.total}"
        }

        sleep(1500)

        //Vuelve a jugar Player 2
        player2.pausa()
        sleep(2000)
        player2.pausa()
        registro2 += "Turno ${player2.numTiro} (${player2.dado})"
        principal.runOnUiThread{
            principal.reg2.text = registro2
            principal.total2.text = "Total: ${player2.total}"
        }

        sleep(1500)


        //Verifica ganador
        if (player1.total > player2.total){
            principal.runOnUiThread{
                principal.txtganador.text = "GANADOR ES Jugador1"
            }
        }else{
            principal.runOnUiThread{
                principal.txtganador.text = "GANADOR ES Jugador 2"
            }
        }

        player1.detener()
        player2.detener()
        sleep(2000)

        //Boton Visble
        principal.runOnUiThread {
            principal.btn.visibility = View.VISIBLE
        }
    }

}

class Player():Thread(){
    var total = 0
    var dado = 0
    var continuar = true
    var pausar = false
    var numTiro = 0

    override fun run() {
        super.run()
        while (continuar){
            if (!pausar){
                tirada()
                sumarTotal(dado)
                numTiro++
            }
            sleep(2000)
        }
    }

    fun detener(){
        continuar = false
    }

    fun pausa(){
        pausar = !pausar
    }

    fun tirada(){
        //maximo 6 minimo 1
        dado = (Math.random()*6).toInt()+1
    }

    fun sumarTotal(a:Int){
        total += a
    }
}