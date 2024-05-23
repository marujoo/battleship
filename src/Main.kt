import java.io.File

var numColunas : Int = -1
var numLinhas : Int = -1

var tabuleiroHumano: Array<Array<Char?>> = emptyArray()
var tabuleiroComputador : Array<Array<Char?>> = emptyArray()

var tabuleiroPalpitesDoHumano : Array<Array<Char?>> = emptyArray()
var tabuleiroPalpitesDoComputador : Array<Array<Char?>> = emptyArray()

fun tamanhoTabuleiroValido (numLinhas : Int? , numColunas : Int?) : Boolean {

    if (numLinhas == null || numColunas == null) {
        return false
    }
    return  (numLinhas == 4 || numLinhas == 5 || numLinhas == 7 || numLinhas == 8 || numLinhas == 10) &&
            (numLinhas == numColunas)
}

fun processaCoordenadas (coordenadas : String? , numLinhas: Int? , numColunas: Int?) : Pair<Int,Int>? {

    if (coordenadas == null || numLinhas == null || numColunas == null) {
        return null
    }

    if (coordenadas.length !in 3..4) {
        return null
    }

    if (coordenadas.length == 3) {

        val numero = coordenadas[0].toString().toIntOrNull()
        val letra = coordenadas[2].uppercaseChar()

        if (numero == null || numero !in 1..numLinhas) {
            return null
        }

        if (letra !in 'A'..('A' + numColunas - 1).toChar()) {
            return null
        }

        return Pair(numero, letra.code - 'A'.code + 1)
    }
    if (coordenadas.length == 4) {

        val numero = (coordenadas[0].toString() + coordenadas[1].toString()).toInt()
        val letra = coordenadas[3].uppercaseChar()





        if (letra !in 'A'..('A' + numColunas - 1).toChar()) {
            return null
        }

        return Pair(numero, (letra.code - 'A'.code) + 1)
    }
    return null
}


fun criaLegendaHorizontal (numColunas : Int) : String {

    if (numColunas in 1..26) {
        var count = 1
        var legenda = ""

        while (count <= numColunas) {
            if (count == numColunas ) {
                legenda += (64 + count).toChar()
            } else {
                legenda += (64 + count).toChar() + " | "
            }
            count++
        }
        return legenda
    }
    return "!! Opcao Invalida"
}

fun criaTerreno(numLinhas: Int?, numColunas: Int?): String {

    if (numLinhas == null || numColunas == null) {
        return "!! Opcao invalida"
    }

    val legenda = "\n" + "| " + criaLegendaHorizontal(numColunas) + " |"
    var terreno = legenda + "\n"

    var linha = 1
    while (linha <= numLinhas) {
        terreno += "|"
        var count = 0
        while (count < numColunas) {
            terreno += " ~ |"
            count++
        }
        terreno += " $linha\n"
        linha++
    }

    return terreno
}

fun menuPrincipal(): Int {
    println()
    println("> > Batalha Naval < <")
    println()
    println("1 - Definir Tabuleiro e Navios")
    println("2 - Jogar")
    println("3 - Gravar")
    println("4 - Ler")
    println("0 - Sair")
    println()

    var entrada = readln()

    while (entrada !in "0".."4") {
        println("!!! Opcao invalida, tente novamente")
        entrada = readln()
    }

    return when (entrada){
        "0" -> 106
        "1" -> 101
        "2" -> 103
        "3" -> 104
        "4" -> 105
        else -> 106
    }
}
fun menuDefinirTabuleiro(): Int {
    println()
    println("> > Batalha Naval < <")
    println()
    println("Defina o tamanho do tabuleiro:")
    println("Quantas linhas?")
    numLinhas = readln().toInt()

    if (numLinhas == -1) {
        return 100
    }

    while ( numLinhas != 4 && numLinhas != 5 && numLinhas != 7 && numLinhas != 8 && numLinhas != 10) {
        println("!!! Numero de linhas invalidas, tente novamente")
        println("Quantas linhas?")
        if (numLinhas == -1) {
            return 100
        }
        numLinhas = readln().toInt()
    }
    println("Quantas colunas?")
    numColunas = readln().toInt()
    if (numColunas == -1) {
        return 100
    }
    while (numColunas != numLinhas) {
        println("!!! Numero de colunas invalidas, tente novamente")
        println("Quantas colunas?")
        if (numColunas == -1) {
            return 100
        }
        numColunas = readln().toInt()
    }

    if (tamanhoTabuleiroValido(numLinhas, numColunas)) {
        println(criaTerreno(numLinhas.toInt(), numColunas.toInt()))
        menuDefinirNavios()
        return 100
    }
    return 101
}

fun menuDefinirNavios(): Int {
    println("Insira as coordenadas do navio:")
    println("Coordenadas? (ex: 6,G)")
    var coordenadas = readln()

    if (coordenadas == "-1") {
        return 100
    }
    var coordenadasValidas = processaCoordenadas(coordenadas, numLinhas, numColunas)

    while (coordenadasValidas == null) { //while (!coordenadasValidas) {
        if (coordenadas == "-1") {
            return 100
        }
        println("!!! Coordenadas invalidas, tente novamente")
        println("Coordenadas? (ex: 6,G)")
        coordenadas = readln()
        coordenadasValidas = processaCoordenadas(coordenadas, numLinhas, numColunas)

    }

    println("Insira a orientacao do navio:")
    println("Orientacao? (N, S, E, O)")
    var orientacao = readln()

    if (orientacao == "-1") {
        return 100
    }
    while (orientacao != "N" && orientacao != "S" && orientacao != "E" && orientacao != "O") {
        if (orientacao == "-1") {
            return 100
        }
        println("!!! Orientacao invalida, tente novamente")
        println("Orientacao? (N, S, E, O)")
        orientacao = readln()
    }
    return 102
}

const val PIMPLEMENTAR = "!!! POR IMPLEMENTAR, tente novamente"

fun menuJogar(): Int {
    println("!!! Tem que primeiro definir o tabuleiro do jogo, tente novamente")
    return 100
}

fun menuLerFicheiro(): Int {
    println(PIMPLEMENTAR)
    return 100
}

fun menuGravarFicheiro(): Int{
    println("Introduza o nome do ficheiro (ex: jogo.txt)")
    val nome = readln()
    gravarJogo(nome, tabuleiroHumano, tabuleiroPalpitesDoHumano, tabuleiroComputador, tabuleiroPalpitesDoComputador)
    println("Tabuleiro $numLinhas"+"x$numColunas gravado com sucesso")


    return 105
}

const val MENU_PRINCIPAL = 100
const val MENU_DEFINIR_TABULEIRO = 101
const val MENU_DEFINIR_NAVIOS = 102
const val MENU_JOGAR = 103
const val MENU_LER_FICHEIRO = 104
const val MENU_GRAVAR_FICHEIRO = 105
const val SAIR = 106

fun calculaNumNavios (numLinhas: Int?, numColunas: Int?) : Array<Int?> {

    if (tamanhoTabuleiroValido(numLinhas, numColunas)) {
        val espacos = numLinhas!! * numColunas!!
        var submarinos = 0
        var contraTorpedeiros = 0
        var naviosTanque = 0
        var portaAvioes = 0
        when {
            espacos == 100 -> {
                portaAvioes = 1
                naviosTanque = 1
                contraTorpedeiros = 2
                submarinos = 3
            }
            espacos == 64 -> {
                portaAvioes = 1
                naviosTanque = 1
                contraTorpedeiros = 2
                submarinos = 2
            }
            espacos == 49 -> {
                portaAvioes = 1
                naviosTanque = 1
                contraTorpedeiros = 1
                submarinos = 2
            }
            espacos == 25 -> {
                portaAvioes = 0
                naviosTanque = 1
                contraTorpedeiros = 1
                submarinos = 1
            }
            else -> {
                submarinos = 2
            }
        }

        return arrayOf(submarinos, contraTorpedeiros, naviosTanque, portaAvioes)
    }

    return arrayOf()
}

fun criaTabuleiroVazio(numLinhas: Int?, numColunas: Int?) : Array<Array<Char?>> {
    if (numLinhas == null || numColunas == null || numLinhas <= 0 || numColunas <= 0) {
        return arrayOf()
    }
    return Array(numLinhas) { Array(numColunas) { null } }
}

fun coordenadaContida (tabuleiro : Array<Array<Char?>>, linha : Int?, coluna : Int?) : Boolean {
    if (linha == null && coluna == null){
        return false
    }

    if (tamanhoTabuleiroValido(linha,coluna)){
        return true
    }

    if (linha in 1..tabuleiro.size  && coluna in 1..tabuleiro.size){
        return true
    }


    return false
}

fun limparCoordenadasVazias (par : Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {
    var posicaoPar = 0
    var paresRemovidos = 0

    while (posicaoPar < par.size) {
        if (par[posicaoPar].first == 0 || par[posicaoPar].second == 0) {
            paresRemovidos++
        }
        posicaoPar++
    }

    val tamanhoParNovo = par.size - paresRemovidos
    val parNovo = Array(tamanhoParNovo) {Pair(0,0)}
    var posicaoParNovo = 0
    posicaoPar = 0


    while (posicaoPar < par.size){

        if (par[posicaoPar].first != 0 && par[posicaoPar].second != 0){
            parNovo[posicaoParNovo] = par[posicaoPar]
            posicaoParNovo++
        }
        posicaoPar++
    }

    return parNovo
}


fun juntarCoordenadas (primeiro : Array<Pair<Int, Int>>, segundo : Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {
    return primeiro + segundo
}

fun gerarCoordenadasNavio (tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?, orientacao : String, dimensao : Int) : Array<Pair<Int, Int>> {

    if (linha == null || coluna == null || linha !in 1..tabuleiro.size || coluna !in 1..tabuleiro.size) {
        return arrayOf()
    }

    if (dimensao == 1) {
        return arrayOf(Pair(linha, coluna)) // Retorna apenas uma coordenada para submarinos
    }

    val coordenadas = Array(dimensao) { Pair(0, 0) }

    val direcaoHorizontal = when (orientacao) {
        "E" -> 1
        "O" -> -1
        else -> 0
    }

    val direcaoVertical = when (orientacao) {
        "N" -> -1
        "S" -> 1
        else -> 0
    }

    for (posicao in 0 until dimensao) {
        val novaLinha = linha + (posicao * direcaoVertical)
        val novaColuna = coluna + (posicao * direcaoHorizontal)

        if (novaLinha in 1..tabuleiro.size && novaColuna in 1..tabuleiro.size) {
            coordenadas[posicao] = Pair(novaLinha, novaColuna)
        } else {
            return arrayOf()
        }
    }

    return coordenadas
}


fun gerarCoordenadasFronteira (tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?, orientacao: String, dimensao: Int): Array<Pair<Int,Int>>{

    val coordenadasNavio = gerarCoordenadasNavio(tabuleiro, linha, coluna, orientacao, dimensao)

    if (coordenadasNavio.isEmpty()) {
        return emptyArray()
    }

    var fronteiras = arrayOf<Pair<Int, Int>>()

    for ((linhaNavio, colunaNavio) in coordenadasNavio) {
        var posicao = 0
        val temporario = Array(9) { Pair(0, 0) }

        for (cima in -1..1) {
            for (baixo in -1..1) {
                val fronteira = linhaNavio + cima to colunaNavio + baixo
                if (coordenadaContida(tabuleiro, fronteira.first, fronteira.second ) && fronteira !in coordenadasNavio) {
                    temporario[posicao] = fronteira
                    posicao++
                }
            }
        }

        fronteiras += temporario
    }

    fronteiras = limparCoordenadasVazias(fronteiras)
    return fronteiras
}

fun estaLivre (tabuleiro: Array<Array<Char?>>, coordenadas : Array<Pair<Int, Int>>) : Boolean {

    val linhas = tabuleiro.size
    val colunas = tabuleiro[0].size

    for ((linha, coluna) in coordenadas) {
        // Verifica se a coordenada estÃ¡ dentro dos limites do tabuleiro
        if (linha -1 < 0 || linha -1 > linhas || coluna -1 < 0 || coluna -1 > colunas) {
            return false
        }

        // Verifica se a coordenada estÃ¡ livre (nÃ£o possui um navio)
        if (tabuleiro[linha -1][coluna -1] != null) {
            return false
        }
    }
    return true
}

fun insereNavioSimples (tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?, dimensao: Int) : Boolean {

    val orientacao = "E" // Assumindo sempre orientacao este

    val coordenadasNavio = gerarCoordenadasNavio(tabuleiro, linha, coluna, orientacao, dimensao)
    val coordenadasFronteira = gerarCoordenadasFronteira(tabuleiro, linha, coluna, orientacao, dimensao)
    val coordenadasTotais = juntarCoordenadas(coordenadasNavio, coordenadasFronteira)

    if (linha == null || coluna == null || coordenadasTotais.isEmpty()) {
        return false
    }

    for ((linhaNavio, colunaNavio) in coordenadasNavio) {
        // Verifica se as coordenadas estÃ£o dentro dos limites do tabuleiro
        if (linhaNavio - 1 < 0 || linhaNavio - 1 >= tabuleiro.size || colunaNavio - 1 < 0 || colunaNavio - 1 >= tabuleiro[0].size) {
            return false
        }

        // Verifica se a coordenada jÃ¡ estÃ¡ preenchida
        if (tabuleiro[linhaNavio - 1][colunaNavio - 1] != null) {
            return false
        }

        tabuleiro[linhaNavio - 1][colunaNavio - 1] = dimensao.toString()[0]
    }

    return true
}


fun insereNavio (tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?, orientacao: String, dimensao: Int) : Boolean {

    val coordenadasNavio = gerarCoordenadasNavio(tabuleiro, linha, coluna, orientacao, dimensao)
    val coordenadasFronteira = gerarCoordenadasFronteira(tabuleiro, linha, coluna, orientacao, dimensao)
    val coordenadasTotais = juntarCoordenadas(coordenadasNavio, coordenadasFronteira)

    if (linha == null || coluna == null || coordenadasTotais.isEmpty() || !estaLivre(tabuleiro, coordenadasTotais)) {
        return false
    }

    for ((linhaNavio, colunaNavio) in coordenadasNavio) {
        tabuleiro[linhaNavio - 1][colunaNavio - 1] = dimensao.toString()[0]
    }

    return true
}

fun preencheTabuleiroComputador (tabuleiro: Array<Array<Char?>>, config: Array<Int>)  {

    var numSubmarinos = config.getOrNull(0) ?: 0
    val numContraTorpedeiros = config.getOrNull(1) ?: 0
    val numNaviosTanque = config.getOrNull(2) ?: 0
    val numPortaAvioes = config.getOrNull(3) ?: 0



    val linha = (1..tabuleiro.size).random()
    val coluna = (1..tabuleiro.size).random()
    val orientacao = arrayOf("N", "S", "E", "O").random()



    if (tamanhoTabuleiroValido(4,4)) {
        numSubmarinos = 2

        insereNavioSimples(tabuleiro,linha,coluna, dimensao = 1)


        val fronteira = gerarCoordenadasFronteira(tabuleiro,linha,coluna,orientacao = "E",dimensao = 1)

        var linha2 = (1..tabuleiro.size).random()
        var coluna2 = (1..tabuleiro.size).random()

        if (Pair(linha2,coluna2) == Pair(linha,coluna)){
            linha2 = (1..tabuleiro.size).random()
            coluna2 = (1..tabuleiro.size).random()
        }

        while (Pair(linha2,coluna2) in fronteira){
            linha2 = (1..tabuleiro.size).random()
            coluna2 = (1..tabuleiro.size).random()
            if (Pair(linha2-1,coluna2-1) == Pair(linha-1,coluna-1)){
                linha2 = (1..tabuleiro.size).random()
                coluna2 = (1..tabuleiro.size).random()
            }
        }
        insereNavioSimples(tabuleiro,linha2,coluna2, dimensao = 1)
    }
}

fun navioCompleto(tabuleiro: Array<Array<Char?>>, linha: Int, coluna: Int): Boolean {

    if (linha !in 1..tabuleiro.size || coluna !in 1..tabuleiro[0].size) {
        return false
    }

    val tipoNavio = tabuleiro[linha - 1][coluna - 1] ?: return false

    val dimensao = when (tipoNavio) {
        '1' -> return true
        '2' -> 2
        '3' -> 3
        '4' -> 4
        else -> return false
    }

    val direcoes = arrayOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
    for (direcao in direcoes) {
        var contagemNavio = 1
        for (i in 1 until dimensao) {
            val novaLinha = linha + direcao.first * i
            val novaColuna = coluna + direcao.second * i
            if (novaLinha in 1..tabuleiro.size && novaColuna in 1..tabuleiro[0].size
                && tabuleiro[novaLinha - 1][novaColuna - 1] == tipoNavio) {
                contagemNavio++
            }
        }
        if (contagemNavio == dimensao) {
            return true
        }
    }
    return false
}

fun obtemMapa (tabuleiro: Array<Array<Char?>>, seletor : Boolean) : Array<String> {

    var mapa : Array<String> = emptyArray<String>()

    mapa += "| " + criaLegendaHorizontal(tabuleiro.size) + " |"
    for (linha in 0.. tabuleiro.size -1 ) {
        var temporario : String = "|"
        for (coluna in 0..tabuleiro[0].size -1 ) {

            if (seletor) {
                temporario += when (tabuleiro[linha][coluna]) {
                    null -> " ~ |"
                    else -> " " + tabuleiro[linha][coluna] + " |"
                }
            } else {
                if (tabuleiro[linha][coluna] == null) {
                    temporario += " ? |"
                } else {
                    if (navioCompleto(tabuleiro, linha + 1, coluna + 1)) {
                        temporario += " " + tabuleiro[linha][coluna] + " |"
                    } else {
                        temporario += when (tabuleiro[linha][coluna]) {
                            '2' -> " \u2082 |"
                            '3' -> " \u2083 |"
                            '4' -> " \u2084 |"
                            else -> " " + tabuleiro[linha][coluna] + " |"
                        }
                    }
                }
            }
        }
        temporario += " " + (linha + 1).toString()
        mapa += temporario
    }
    return mapa
}

fun lancarTiro (tabuleiroDoComputador: Array<Array<Char?>>, tabuleiroPalpitesDoHumano: Array<Array<Char?>>, coordenadas: Pair<Int, Int>) : String {

    val (linha, coluna) = coordenadas
    val navio = tabuleiroDoComputador[linha-1][coluna-1]

    // Atualiza o tabuleiro de palpites do humano com o palpite do utilizador
    tabuleiroPalpitesDoHumano[linha-1][coluna-1] = if (navio != null) navio else 'X'

    return when (navio) {
        '1' -> "Tiro num submarino."
        '2' -> "Tiro num contra-torpedeiro."
        '3' -> "Tiro num navio-tanque."
        '4' -> "Tiro num porta-aviÃµes."
        else -> "Agua."
    }
}

fun geraTiroComputador(tabuleiroPalpitesComputador: Array<Array<Char?>>): Pair<Int, Int> {

    var lugarTiro : Pair<Int,Int>

    do {
        lugarTiro = Pair(
            (1..tabuleiroPalpitesComputador.size).random(),
            (1..tabuleiroPalpitesComputador.size).random()
        )
    } while (tabuleiroPalpitesComputador[lugarTiro.first-1][lugarTiro.second-1] == 'X' ||
        tabuleiroPalpitesComputador[lugarTiro.first-1][lugarTiro.second-1] != null)

    return lugarTiro
}

fun contarNaviosDeDimensao (tabuleiroPalpitesDoHumano: Array<Array<Char?>>, dimensao: Int) : Int {

    val linhas = tabuleiroPalpitesDoHumano.size
    val colunas = tabuleiroPalpitesDoHumano[0].size
    var countNavios = 0

    for (linha in 0 until linhas) {
        for (coluna in 0 until colunas) {
            if (navioCompleto(tabuleiroPalpitesDoHumano, linha + 1, coluna + 1)
                && tabuleiroPalpitesDoHumano[linha][coluna] == dimensao.toString()[0]) {
                countNavios++
            }
        }
    }

    return countNavios
}


fun venceu (tabuleiroPalpitesDoHumano: Array<Array<Char?>>) : Boolean {

    val numNaviosHumano = calculaNumNavios(tabuleiroHumano.size, tabuleiroHumano[0].size)

    for (dimensao in 1..4) {
        val countNavios = contarNaviosDeDimensao(tabuleiroPalpitesDoHumano, dimensao)
        if (countNavios != (numNaviosHumano[dimensao - 1] ?: 0)) {
            return false
        }
    }
    return true
}

const val player = "Jogador"
const val computador = "Computador"
const val real = "Real"
const val palpites = "Palpites"

fun lerJogo(nomeDoFicheiro: String , tipoDeTabuleiro: Int ): Array<Array<Char?>> {

    val file = File(nomeDoFicheiro)
    val linhas = file.readLines()

    val tamanhoTabuleiro = linhas[0].split(",")
    val numLinhas = tamanhoTabuleiro.getOrElse(0) { "0" }.toIntOrNull() ?: 0
    val numColunas = tamanhoTabuleiro.getOrElse(1) { "0" }.toIntOrNull() ?: 0

    val tabuleiroLido = criaTabuleiroVazio(numLinhas, numColunas)
    var index = -1


    for (linha in 1 until linhas.size) {
        when (tipoDeTabuleiro) {
            1 -> {
                if (linhas[linha].contains(player) && linhas[linha +1].contains(real)) {
                    index = linha + 2
                }
            }
            2 -> {
                if (linhas[linha].contains(player) && linhas[linha +1].contains(palpites)) {
                    index = linha + 2
                }
            }
            3 -> {
                if (linhas[linha].contains(computador) && linhas[linha +1].contains(real)) {
                    index = linha + 2
                }
            }
            4 -> {
                if (linhas[linha].contains(computador) && linhas[linha +1].contains(palpites)) {
                    index = linha + 2
                }
            }
        }
    }

    for (i in 0 until  numLinhas) {
        val conteudoLinha = linhas[i + index].split(",")
        for (j in 0 until conteudoLinha.size) {
            val caracter = when (conteudoLinha[j]) {
                "" -> null
                else -> conteudoLinha[j][0]
            }
            if (i in tabuleiroLido.indices && j in tabuleiroLido[i].indices) {
                tabuleiroLido[i][j] = caracter
            }
        }
    }
    return tabuleiroLido
}

fun gravarJogo(
    nomeDoFicheiro: String,
    tabuleiroHumano: Array<Array<Char?>>,
    tabuleiroPalpitesHumano: Array<Array<Char?>>,
    tabuleiroRealComputador: Array<Array<Char?>>,
    tabuleiroPalpitesComputador: Array<Array<Char?>>
) {
    val file = File(nomeDoFicheiro)
    val printer = file.printWriter()
    printer.println(tabuleiroHumano.size.toString() + "," + tabuleiroHumano[0].size.toString())
    printer.println()
    val tabuleiros = arrayOf(Pair(player, real), Pair(player, palpites), Pair(computador, real), Pair(computador, palpites))
    for ((jogador,tipo) in tabuleiros) {

        printer.println(jogador)
        printer.println(tipo)
        val tabuleiro = when (jogador) {
            player -> if (tipo == real) tabuleiroHumano else tabuleiroPalpitesHumano
            else -> if (tipo == real) tabuleiroRealComputador else tabuleiroPalpitesComputador
        }
        for (linha in tabuleiro) {
            printer.println(linha.joinToString(",") { it?.toString() ?: "" })
        }
        if (!(jogador == computador && tipo == palpites)) {
            printer.println()
        }
    }
    printer.close()
}



fun main() {
    var menuActual = MENU_PRINCIPAL
    while (true) {
        menuActual = when (menuActual) {
            MENU_PRINCIPAL -> menuPrincipal()
            MENU_DEFINIR_TABULEIRO -> menuDefinirTabuleiro()
            MENU_DEFINIR_NAVIOS -> menuDefinirNavios()
            MENU_JOGAR -> menuJogar()
            MENU_LER_FICHEIRO -> menuLerFicheiro()
            MENU_GRAVAR_FICHEIRO -> menuGravarFicheiro()
            SAIR -> return
            else -> return
        }
    }
}