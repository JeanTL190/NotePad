import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import javax.swing.*
import javax.swing.filechooser.FileSystemView

//Janela Principal
public class Janela : JFrame {

    //Barra de menu
    private val barraMenu: JMenuBar
    //Menus
    private val menuArq: JMenu
    private val exibir: JMenu
    //Itens do menu
    private val criarNota: JMenuItem
    private val addNota: JMenuItem
    private val about:JMenuItem
    private val sair: JMenuItem
    //Janela Interna
    private val theDesktop: JDesktopPane
    //Escolher arquivo
    private var choose: JFileChooser? = null
    //Ler arquivo
    private var readArq: BufferedReader?=null

    constructor() : super("Notepad") {
        barraMenu = JMenuBar()
        about = JMenuItem("Sobre")
        menuArq = JMenu("Arquivo")
        exibir = JMenu("Exibir")
        criarNota = JMenuItem("Criar Nota")
        addNota = JMenuItem("Add Nota")
        sair = JMenuItem("Sair")
        theDesktop = JDesktopPane()
        barraMenu.add(menuArq)
        menuArq.add(criarNota)
        menuArq.add(addNota)
        menuArq.add(sair)
        barraMenu.add(exibir)
        exibir.add(about)
        super.setJMenuBar(barraMenu)

        criarNota.addActionListener({
            val janelaInterna = Notepad(theDesktop)
            janelaInterna.setSize(300, 300)
            janelaInterna.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
            janelaInterna.isVisible = true
        })

        addNota.addActionListener({
            choose = JFileChooser(File("C:\\Users\\Particular\\IdeaProjects\\ProgAplicada"))
            choose!!.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            val i = choose!!.showOpenDialog(choose)
            var t:String = ""
            if(i!=JFileChooser.CANCEL_OPTION)
            {
                readArq = BufferedReader(FileReader(choose!!.getSelectedFile()))
                val x = readArq!!.readLines()
                for(cont in x)
                {
                    t+=cont+"\n"
                }
                val nota = Cartao(t,File(choose.toString()).nameWithoutExtension)
                nota!!.setSize(300, 300)
                nota!!.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nota!!.isVisible = true
                theDesktop.add(nota)
            }
        })

        about.addActionListener({
            JOptionPane.showMessageDialog(null," ECOM04 – Linguagem de Programação\n ANDRE HITOSHI YAMAMOTO\nCARLOS CESAR REZENDE BUENO\nJEAN TAN LI\nJOAO LUCAS BERLINCK CAMPOS\nYURI HENRIQUE DE ALMEIDA MUNIZ\n")
        })
        sair.addActionListener({
            System.exit(0)
        })
        theDesktop.layout = GridLayout(2, 4)
        super.add(theDesktop)
    }
}

//Cria a anotação
public class Notepad : JFrame {

    private var painelNorte: JPanel
    private var label: JLabel
    private var textoNorte: JTextField
    private var textCenter: JTextArea
    private var butao: JButton
    private var nota: Cartao? = null
    private var arq: Formatter? = null
    private var scroll: JScrollPane?=null

    constructor(desktop: JDesktopPane) : super("Nota") {
        painelNorte = JPanel();
        label = JLabel("Nome do arquivo: ")
        textoNorte = JTextField()
        textCenter = JTextArea()
        butao = JButton("Salvar")
        scroll = JScrollPane(textCenter)
        painelNorte.layout = GridLayout(1, 2)
        painelNorte.add(label)
        painelNorte.add(textoNorte)
        add(painelNorte, BorderLayout.NORTH)
        add(scroll, BorderLayout.CENTER)
        add(butao, BorderLayout.SOUTH)

        butao.addActionListener({
            if (textoNorte.getText() != "") {
                arq = Formatter(textoNorte.getText() + ".txt")
                arq!!.format(textCenter.getText())
                nota = Cartao(textCenter.getText(), textoNorte.getText())
                arq!!.close()
                dispose()
                nota!!.setSize(300, 300)
                nota!!.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nota!!.isVisible = true

                desktop.add(nota)
            }
        })
    }
}

//Cria Cartão na Janela Interna
class Cartao : JInternalFrame {
    private val texto: String
    private val txtArea: JTextArea
    private val scroll: JScrollPane

    constructor(texto: String, nome: String) : super(nome, false, true, false, true) {
        //super.setBackground(Color.YELLOW)
        this.texto = texto
        txtArea = JTextArea(30, 50)
        txtArea.background = Color.decode("#022601")
        txtArea.isEditable = false
        txtArea.font = Font("Monospaced", Font.ITALIC, 25)
        txtArea.foreground = Color.green
        txtArea.text = texto
        scroll = JScrollPane(txtArea)
        add(scroll)
    }

}

fun main(args: Array<String>) {

    val janela = Janela()
    janela.setSize(600, 480)
    janela.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    janela.isVisible = true
    janela.extendedState = Frame.MAXIMIZED_BOTH

}
