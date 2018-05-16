/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;
import MyUtils.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

/**
 *
 * @author Rafael Vasquez
 * @author Raquel Escalante
 */
public class MainInterface extends javax.swing.JFrame {
    // Creating filter controller class
    private final FiltersController myFilters;
    // Creating a JLabel to display image.
    private final JLabel imglabel;
    // Creating more jLabels for histograms
    private final JLabel rLabel;
    private final JLabel grLabel;
    private final JLabel bLabel;
    // Creating a file chooser to open files with
    private final JFileChooser fcOpen;
    // Creating a file chooser to save files with
    private final JFileChooser fcSave;
    // Properties of the Image
    private int width;
    private int height;
    private BufferedImage img = null;
    // Hashmap and counter used to count unique colors
    private final HashMap<Integer, Integer> uniqueCols;
    private int colorsCounter;
    private final JSlider thresholdSlider;
    private final JSlider kernelSizeSlider;
    private final JSlider brightnessSlider;
    //Integer to show bits per pixel
    private int bitspp;
    //Histogram bins
    private int [] GrayBins;
    private int [] RedBins;
    private int [] GreenBins;
    private int [] BlueBins;
    //Undo/Redo Deques
    private Deque<BufferedImage> UndoDeque;
    private Deque<BufferedImage> RedoDeque;
    
    
    /**
     * Creates new form MainInterface
     */
    public MainInterface() {
        initComponents();
        myFilters = new FiltersController();
        imglabel = new JLabel();
				//Labels for histograms;
        rLabel = new JLabel();
        grLabel = new JLabel();
        bLabel = new JLabel();
				bitspp = 0;
        fcOpen = new JFileChooser();
        fcSave = new JFileChooser();
        // Creating file filters for the file choosers
        FileNameExtensionFilter imagesFilter = new FileNameExtensionFilter("Imágenes: *.bmp, *.jpg, *.png, *.pbm, *.pgm, *.ppm", "bmp", "jpg", "png", "pbm", "pgm", "ppm");
        FileNameExtensionFilter savingFilter = new FileNameExtensionFilter("Imágenes RLE: *.rle", "rle");
        fcOpen.addChoosableFileFilter(imagesFilter);
        fcOpen.addChoosableFileFilter(savingFilter);
        fcOpen.setFileFilter(imagesFilter);
        
//        fcSave.addChoosableFileFilter(savingFilter);
//        fcSave.setFileFilter(savingFilter);
        
        uniqueCols = new HashMap<>();
        colorsCounter = 0;
        // Configuring some Slider's properties.
        thresholdSlider = new JSlider(0, 255, 127); // min, max, init
        thresholdSlider.setMajorTickSpacing(50);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        
        // Configuring some Slider's properties.
        kernelSizeSlider = new JSlider(2, 7, 5); // min, max, init
        kernelSizeSlider.setMajorTickSpacing(1);
        kernelSizeSlider.setPaintTicks(true);
        kernelSizeSlider.setPaintLabels(true);
        
        // Configuring some Slider's properties.
        brightnessSlider = new JSlider(-127, 127, 0); // min, max, init
        brightnessSlider.setMajorTickSpacing(50);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);
        
        //Creating deques for undo and redo functions
        UndoDeque = new ArrayDeque<BufferedImage>(4);
        RedoDeque = new ArrayDeque<BufferedImage>(4);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScrollPanePanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        ImageInfoPanel = new javax.swing.JPanel();
        InfoPanel = new javax.swing.JPanel();
        InfoLabel = new javax.swing.JLabel();
        Dimensiones = new javax.swing.JLabel();
        BPP = new javax.swing.JLabel();
        Colores = new javax.swing.JLabel();
        BarraEstadoPanel = new javax.swing.JPanel();
        Estado = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        MenuArchivo = new javax.swing.JMenu();
        AbrirArchivo = new javax.swing.JMenuItem();
        GuardarImagen = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        DeshacerOperacion = new javax.swing.JMenuItem();
        RehacerOperacion = new javax.swing.JMenuItem();
        MenuFiltros = new javax.swing.JMenu();
        ColorMenu = new javax.swing.JMenu();
        EscalaDeGrises = new javax.swing.JMenuItem();
        BlancoNegro = new javax.swing.JMenuItem();
        ReduccionColorMenu = new javax.swing.JMenu();
        ReduccionBitsPorPixel = new javax.swing.JMenuItem();
        PaletaAleatoria = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        UmbralizacionMenu = new javax.swing.JMenu();
        OTSUOpenCV = new javax.swing.JMenuItem();
        OTSUPropio = new javax.swing.JMenuItem();
        Triangulo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        MorfologiaMenu = new javax.swing.JMenu();
        Erosion = new javax.swing.JMenuItem();
        Dilatacion = new javax.swing.JMenuItem();
        Apertura = new javax.swing.JMenuItem();
        Cierre = new javax.swing.JMenuItem();
        MenuAyuda = new javax.swing.JMenu();
        Readme = new javax.swing.JMenuItem();
        About = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editor de Imagenes | by Raquel Escalante & Rafael Vasquez");

        InfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        InfoLabel.setText("Información de la imagen");

        Dimensiones.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        Dimensiones.setText("Dimensiones: ");

        BPP.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        BPP.setText("Bits por pixel:");

        Colores.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        Colores.setText("Colores únicos:");

        javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
        InfoPanel.setLayout(InfoPanelLayout);
        InfoPanelLayout.setHorizontalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BPP)
                            .addComponent(Dimensiones)
                            .addComponent(Colores)))
                    .addComponent(InfoLabel))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        InfoPanelLayout.setVerticalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dimensiones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BPP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Colores)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ImageInfoPanelLayout = new javax.swing.GroupLayout(ImageInfoPanel);
        ImageInfoPanel.setLayout(ImageInfoPanelLayout);
        ImageInfoPanelLayout.setHorizontalGroup(
            ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImageInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ImageInfoPanelLayout.setVerticalGroup(
            ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImageInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(527, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ScrollPanePanelLayout = new javax.swing.GroupLayout(ScrollPanePanel);
        ScrollPanePanel.setLayout(ScrollPanePanelLayout);
        ScrollPanePanelLayout.setHorizontalGroup(
            ScrollPanePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ScrollPanePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ImageInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ScrollPanePanelLayout.setVerticalGroup(
            ScrollPanePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane)
            .addComponent(ImageInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        BarraEstadoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Estado.setText("Bienvenido");

        javax.swing.GroupLayout BarraEstadoPanelLayout = new javax.swing.GroupLayout(BarraEstadoPanel);
        BarraEstadoPanel.setLayout(BarraEstadoPanelLayout);
        BarraEstadoPanelLayout.setHorizontalGroup(
            BarraEstadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraEstadoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Estado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BarraEstadoPanelLayout.setVerticalGroup(
            BarraEstadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarraEstadoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Estado)
                .addContainerGap())
        );

        MenuArchivo.setText("Archivo");

        AbrirArchivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        AbrirArchivo.setText("Abrir Imagen...");
        AbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirArchivoActionPerformed(evt);
            }
        });
        MenuArchivo.add(AbrirArchivo);

        GuardarImagen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        GuardarImagen.setText("Guardar imagen...");
        GuardarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarImagenActionPerformed(evt);
            }
        });
        MenuArchivo.add(GuardarImagen);
        MenuArchivo.add(jSeparator1);

        DeshacerOperacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        DeshacerOperacion.setText("Deshacer");
        DeshacerOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeshacerOperacionActionPerformed(evt);
            }
        });
        MenuArchivo.add(DeshacerOperacion);

        RehacerOperacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        RehacerOperacion.setText("Rehacer");
        RehacerOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RehacerOperacionActionPerformed(evt);
            }
        });
        MenuArchivo.add(RehacerOperacion);

        MenuBar.add(MenuArchivo);

        MenuFiltros.setText("Filtros");

        ColorMenu.setText("Color");

        EscalaDeGrises.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        EscalaDeGrises.setText("Escala de Grises");
        EscalaDeGrises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EscalaDeGrisesActionPerformed(evt);
            }
        });
        ColorMenu.add(EscalaDeGrises);

        BlancoNegro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        BlancoNegro.setText("Blanco y Negro");
        BlancoNegro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlancoNegroActionPerformed(evt);
            }
        });
        ColorMenu.add(BlancoNegro);

        ReduccionColorMenu.setText("Reducción de Color");

        ReduccionBitsPorPixel.setText("Reducir bits por pixel");
        ReduccionBitsPorPixel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReduccionBitsPorPixelActionPerformed(evt);
            }
        });
        ReduccionColorMenu.add(ReduccionBitsPorPixel);

        PaletaAleatoria.setText("Paleta Aleatoria");
        PaletaAleatoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaletaAleatoriaActionPerformed(evt);
            }
        });
        ReduccionColorMenu.add(PaletaAleatoria);

        ColorMenu.add(ReduccionColorMenu);

        MenuFiltros.add(ColorMenu);
        MenuFiltros.add(jSeparator2);

        UmbralizacionMenu.setText("Umbralización");

        OTSUOpenCV.setText("OTSU (OpenCV)");
        OTSUOpenCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OTSUOpenCVActionPerformed(evt);
            }
        });
        UmbralizacionMenu.add(OTSUOpenCV);

        OTSUPropio.setText("OTSU (Propio)");
        OTSUPropio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OTSUPropioActionPerformed(evt);
            }
        });
        UmbralizacionMenu.add(OTSUPropio);

        Triangulo.setText("Triangulo");
        Triangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrianguloActionPerformed(evt);
            }
        });
        UmbralizacionMenu.add(Triangulo);

        MenuFiltros.add(UmbralizacionMenu);
        MenuFiltros.add(jSeparator3);

        MorfologiaMenu.setText("Morfología");

        Erosion.setText("Erosión");
        Erosion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ErosionActionPerformed(evt);
            }
        });
        MorfologiaMenu.add(Erosion);

        Dilatacion.setText("Dilatación");
        Dilatacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DilatacionActionPerformed(evt);
            }
        });
        MorfologiaMenu.add(Dilatacion);

        Apertura.setText("Apertura");
        Apertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AperturaActionPerformed(evt);
            }
        });
        MorfologiaMenu.add(Apertura);

        Cierre.setText("Cierre");
        Cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CierreActionPerformed(evt);
            }
        });
        MorfologiaMenu.add(Cierre);

        MenuFiltros.add(MorfologiaMenu);

        MenuBar.add(MenuFiltros);

        MenuAyuda.setText("Ayuda");

        Readme.setText("Léeme");
        Readme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadmeActionPerformed(evt);
            }
        });
        MenuAyuda.add(Readme);

        About.setText("Acerca De...");
        About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutActionPerformed(evt);
            }
        });
        MenuAyuda.add(About);

        MenuBar.add(MenuAyuda);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPanePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BarraEstadoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPanePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BarraEstadoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private IplImage toIplImage(BufferedImage bufImage) {
        ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }
    
    private  BufferedImage toBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame,1);
    }

    private int OTSUThreshold(int[] histogram, int total){
        int threshold = 0;
        double sum = 0;
        double sumB = 0;
        double maxVariance = 0;
        int weightB = 0;
        int weightF;

        for (int t = 0 ; t < 256 ; t++){
            sum += t * histogram[t];
        }

        for (int t = 0 ; t < 256 ; t++){
            weightB += histogram[t];               // Weight Background
            if (weightB == 0) continue;

            weightF = total - weightB;             // Weight Foreground
            if (weightF == 0) break;

            sumB += (float) (t * histogram[t]);

            double mB = sumB / weightB;            // Mean Background
            double mF = (sum - sumB) / weightF;    // Mean Foreground

            // Calculate between class variance
            double varBetween = (double)weightB * (double)weightF * (mB - mF) * (mB - mF);

            // Check for new maximum found
            if (varBetween > maxVariance) {
               maxVariance = varBetween;
               threshold = t;
            }
        }
        return threshold;
    }
    
    private double colorRGBDifference(int c1, int c2){
        int r1 = ((c1 >> 16) & 0xff);
        int g1 = ((c1 >> 8) & 0xff);
        int b1 = c1  & 0xff;
        int r2 = ((c2 >> 16) & 0xff);
        int g2 = ((c2 >> 8) & 0xff);
        int b2 = c2  & 0xff;
        int deltaR = r1 - r2;
        int deltaG = g1 - g2;
        int deltaB = b1 - b2;
        return Math.sqrt( deltaR * deltaR + deltaG * deltaG + deltaB * deltaB );
    }

    private BufferedImage euclideanDistanceColorQuantization(int[] palette){
        BufferedImage destImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int currentColor;
        int minDistanceIndex = 0;
        double minD;
        double minDistance = 99999999;
        for (int i = 0; i < img.getHeight(); i++){
            for(int j = 0; j < img.getWidth(); j++){
                currentColor = img.getRGB(j, i);
                // Now we choose the closest color in RGB space with euclidean distance (not RGBlab).
                for(int k = 0; k < palette.length; k++){
                    minD = colorRGBDifference(currentColor, palette[k]);
                    if(minD < minDistance ){
                        minDistance = minD;
                        minDistanceIndex = k;
                    }
                }
                destImage.setRGB(j, i, palette[minDistanceIndex]);
                minDistance = 99999999;
            }
        }
        return destImage;
    }

    private int[] randomPalette(int n){
        int[] colors = new int[n];
        ArrayList colorsAL = new ArrayList();
        Random randGen = new Random();
        int randHeight, randWidth, randColor;

        while(colorsAL.size() < n){
            randHeight = randGen.nextInt(height);
            randWidth = randGen.nextInt(width);
            randColor = img.getRGB(randWidth, randHeight);

            if(colorsAL.indexOf(randColor) == -1){
                colorsAL.add(randColor);
            }
        }
        for (int i=0; i < colorsAL.size(); i++)
        {
            colors[i] = (int)colorsAL.get(i);
        }

        return colors;
    }

    private IplConvKernel generateCustomStructuringEl(int w, int h){
        SpinnerNumberModel[] spinnerModels = new SpinnerNumberModel[w*h];
        JSpinner[] strElValues = new JSpinner[w*h];
        int[] vals;
        JPanel[] panels = new JPanel[h];
        
        Object[] options = {"Aceptar", "Cancelar"};
        Object[] params;
        IplConvKernel element;
        
        for(int i = 0; i < w * h; i++){
            spinnerModels[i] = new SpinnerNumberModel(1, 0, 1, 1);  // Initial value, min, max, step
            strElValues[i] = new JSpinner(spinnerModels[i]);
        }
        
        for(int i = 0; i < h; i++){
            panels[i] = new JPanel();
            for(int j = 0; j < w ; j++){
                panels[i].add(strElValues[i * w + j]);
            }
        }
        params = new Object[]{"Valores del Elemento Estructurante:",panels};
        int result = JOptionPane.showOptionDialog(  ScrollPanePanel,
                                                    params,
                                                    "Valores del Elemento Estructurante",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE,
                                                    null,           // Don't use a custom Icon
                                                    options,        // The strings of buttons
                                                    options[0]);    // Default button title
        if ( result == JOptionPane.NO_OPTION){
            return null;
        }
        //Obtaining each spinner value and adding it to a int array.
        vals = new int[w*h];
        for(int i = 0; i < w * h; i++){
            vals[i] = (int)strElValues[i].getValue();
        }
        
        element = cvCreateStructuringElementEx(w, h, w/2, h/2, CV_SHAPE_CUSTOM, vals);
        return element;
    }

    private void countUniqueColors(){
        colorsCounter = 0;
        uniqueCols.clear();
        int key;
        if (img != null){
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    // Unpacking the color data of each pixel.
                    key = img.getRGB(x,y);
                    
                    // If the color has not been counted.
                    if (uniqueCols.get(key) == null){
                        uniqueCols.put(key, 1);
                        colorsCounter++;
                    } 
                }
            }
        }
    }
    
    private void updateDimensions(){
        if (img != null){
            width = img.getWidth();
            height = img.getHeight();
        } 
    }
		
    private void getBitsPerPixel(){
        if (img != null){
            ColorModel cm = img.getColorModel();
            bitspp = cm.getPixelSize();         
        }
    }

    private void refreshImageInformation(String msg){
        getBitsPerPixel(); 
        Estado.setText( msg );
        Colores.setText("Colores únicos: "+colorsCounter);
        Dimensiones.setText("Dimensiones: " + width + "x" + height);
        BPP.setText("Bits por pixel: " + bitspp);
    }
    
    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    private void refreshImageDisplayed(boolean count){
        ImageIcon icon = new ImageIcon(img);
        // Adding the ImageIcon to the Label.
        imglabel.setIcon( icon );
        //Aligning the image to the center.
        imglabel.setHorizontalAlignment(JLabel.CENTER);
        //Adding the label to the Scrolling pane.
        jScrollPane.getViewport().add(imglabel);
        // Repainting the scroll pane to update the changes
        jScrollPane.repaint();

        if(count){
            // Recounting colors
            countUniqueColors();
        }
    }
    
    private void OverwriteDeque(Deque d, int size){
        if (d.size() == 4){
            d.removeLast();
        }
    }
    
    private void ClearDeque(Deque d){
        d.clear();
    }
    
    //Sets the content of the desk when opening the image
    private void ImageOpeningDequeSetting(Deque u, Deque r, BufferedImage i){
        ClearDeque(u);
        ClearDeque(r);
        u.push(i);        
    }
    
    //Sets the content of the deques after applying any operation
    private void ImageOperationDequeSetting(Deque u, Deque r, BufferedImage i){
        OverwriteDeque(u, 4);
        u.push(i);
        ClearDeque(r);
    }
    
    private void AbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirArchivoActionPerformed
        //FileInputStream in = null;
        //StreamTokenizer parser;
        //BufferedReader reader;
        //In response to a button click, the file chooser is displayed
        int returnVal = fcOpen.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // The image variable
            img = null;
            IplImage image = null;
            File file = fcOpen.getSelectedFile();
            String path = file.getAbsolutePath();
            String extension = path.substring(path.length() - 3);

            if(null != extension){
                // Are we opening a bmp image?
                switch (extension) {
                    case "bmp":
                    case "jpg":
                    case "png":
                    try {
                        // Filling BufferedImage with file information
                        img = ImageIO.read(file);
                        image = cvLoadImage(path);
                        //ColorModel cm =  img.getColorModel();
                        //int color = img.getRGB(0,0);
                        //JOptionPane.showMessageDialog(this, "Modelo de color:\n" +cm+"\nRGB color:\n" + color );
                        // Making note of image properties
                        updateDimensions();
                        //JOptionPane.showMessageDialog(this, "Imagen tipo: " + types);
                    } catch (IOException e) {
                        // Report exceptions
                        JOptionPane.showMessageDialog(this, "Error al Abrir Imagen!");
                    }
                    break;
                }
                getBitsPerPixel();
                if (image != null && bitspp == 1) {
                    img = toBufferedImage(image);
                }

//                ilpImage = toIplImage(img);
//                img = toBufferedImage(ilpImage);

//                img = null;
//                img = toBufferedImage(ilpImage);
//                ColorModel cm =  img.getColorModel();
//                int color = img.getRGB(0,0);
//                JOptionPane.showMessageDialog(this, "Modelo de color:\n" +cm+"\nRGB color:\n" + color );

//                BufferedImage indexedImage = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
//                Graphics2D g = indexedImage.createGraphics();
//                g.drawImage(img, 0,0,null);
//                img=indexedImage;
                
                //Setting the undo/redo deques for first use per opened image
                ImageOpeningDequeSetting(UndoDeque, RedoDeque, img);
                
                //Refresh image
                refreshImageDisplayed(true);

                // Counting unique colors
                countUniqueColors();

                //Changing Estado Label
                Estado.setText("Abriendo " + file.getAbsolutePath() );
                Colores.setText("Colores únicos: "+colorsCounter);
                Dimensiones.setText("Dimensiones: " + width + "x" + height);
                BPP.setText("Bits por pixel: " + bitspp);

                //Changing Estado Label
                Estado.setText("Abriendo " + file.getAbsolutePath());
            }
        } else {
            // Cancel opening.
            //JOptionPane.showMessageDialog(this, "Opening file canceled.");
            //log.append("Open command cancelled by user." + newline);
        }
    }//GEN-LAST:event_AbrirArchivoActionPerformed

    private void GuardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarImagenActionPerformed
        int returnVal;
        if ( img != null ){
            returnVal = fcSave.showSaveDialog(this);
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
            return;
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fcSave.getSelectedFile();
                // Getting the image extension.
                String path = file.getAbsolutePath();
                String extension = path.substring(path.length() - 3);
                if ("bmp".equals(extension) || "png".equals(extension) || "jpg".equals(extension)){
                    ImageIO.write(img, extension, new File(fcSave.getSelectedFile().getAbsolutePath()));
                    Estado.setText("Imagen guardada en: " + fcSave.getSelectedFile().getAbsolutePath());
                }
            } catch ( IOException e) {
                JOptionPane.showMessageDialog(this, "¡ERROR: Ocurrio un error al guardar la imagen!");
            }
        }
    }//GEN-LAST:event_GuardarImagenActionPerformed

    private void ReadmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReadmeActionPerformed
        Runtime rt = Runtime.getRuntime();
        String readme = ("README.txt");
        try {
            Process p = rt.exec("notepad "+readme);
        } catch (IOException ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReadmeActionPerformed

    private void AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutActionPerformed
        JOptionPane.showMessageDialog(this, "Editor de Imágenes\nRaquel Escalante y Rafael Vasquez\nSemestre 2-2017\nProcesamiento Digital De Imágenes", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AboutActionPerformed

    private void BlancoNegroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlancoNegroActionPerformed
        if (img != null){
            Object[] params = {"Valor del umbral: ", thresholdSlider};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog(  ScrollPanePanel,
                params,
                "Opciones de Umbralización",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title
            //If the operation was canceled do nothing.
            if (result == JOptionPane.NO_OPTION){
                return;
            }
            img = myFilters.ThresholdBlackAndWhite(img, thresholdSlider.getValue());
            
            
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Umbralización a Blanco y Negro.");
            //Estado.setText("Aplicando Blanco y Negro | Colores Únicos en imagen: " + colorsCounter);
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_BlancoNegroActionPerformed

    private void EscalaDeGrisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EscalaDeGrisesActionPerformed
        if (img != null){
            ColorModel cm = img.getColorModel();
            bitspp = cm.getPixelSize();
            if(bitspp == 24){
                IplImage auxImage = toIplImage(img);
                IplImage temp = cvCreateImage(cvGetSize(auxImage), IPL_DEPTH_8U, 1);
                cvCvtColor(auxImage, temp, CV_RGB2GRAY);
                img = toBufferedImage(temp);
                ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
                refreshImageDisplayed(true);
                refreshImageInformation("Aplicando Escala de Grises.");
            }else{
                JOptionPane.showMessageDialog(this, "¡La imágen no puede ser convertida a escala de grises!");
            }
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_EscalaDeGrisesActionPerformed

    private void DilatacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DilatacionActionPerformed
        if (img != null){
            IplConvKernel element;
            int w;
            int h;
            //Jpanel for the button groups
            JPanel optionPanel = new JPanel();
            JPanel sizePanel = new JPanel();
            JRadioButton def = new JRadioButton("Default");
            JRadioButton custom = new JRadioButton("Personalizado");
            JRadioButton s3 = new JRadioButton("3");
            JRadioButton s5 = new JRadioButton("5");
            JRadioButton s7 = new JRadioButton("7");
            ButtonGroup optionGroup = new ButtonGroup();
            ButtonGroup sizeGroup = new ButtonGroup();

            //adding to groups
            optionGroup.add(def);
            optionGroup.add(custom);
            def.setSelected(true);
            sizeGroup.add(s3);
            sizeGroup.add(s5);
            sizeGroup.add(s7);
            s5.setSelected(true); 
            //adding to panels
            optionPanel.add(def);
            optionPanel.add(custom);
            sizePanel.add(s3);
            sizePanel.add(s5);
            sizePanel.add(s7);

            Object[] params = {"Elemento Estructurante:", optionPanel, "En caso de ser personalizado, indique ancho y alto del elemento estructurante:", sizePanel};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog( ScrollPanePanel,
                params,
                "Opciones del Elemento Estructurante",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title

            String opt = getSelectedButtonText(optionGroup);

            if (result == JOptionPane.NO_OPTION){
                // If the kernel is 1x1 the image ends up the same or if the user cancels the action
                // return at once.
                return;
            }

            switch(opt){
                case "Default":
                    //It uses a rectangular structuring element by default
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
                    break;
                case "Personalizado":
                    String size = getSelectedButtonText(sizeGroup);
                    w = h = Integer.parseInt(size);
                    element = generateCustomStructuringEl(w, h);
                    break;
                default:
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
            }

            IplImage ilpImage2 = toIplImage(img);
            cvDilate(ilpImage2, ilpImage2, element, 1);
            img = toBufferedImage(ilpImage2);
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Dilatación");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_DilatacionActionPerformed

    private void OTSUOpenCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OTSUOpenCVActionPerformed
        if (img != null){
            IplImage ilpImage2 ;
            IplImage ilpImageGray;
            ColorModel cm = img.getColorModel();
            bitspp = cm.getPixelSize();
            switch(bitspp){
                case 24:
                    ilpImage2 = toIplImage(img);
                    ilpImageGray = cvCreateImage(cvGetSize(ilpImage2), IPL_DEPTH_8U, 1);
                    cvCvtColor(ilpImage2, ilpImageGray, CV_RGB2GRAY);
                    cvThreshold(ilpImageGray, ilpImageGray, 0, 255, CV_THRESH_BINARY | CV_THRESH_OTSU);
                    img = toBufferedImage(ilpImageGray);
                    break;
                case 8:
                    ilpImage2 = toIplImage(img);
                    cvThreshold(ilpImage2, ilpImage2, 0, 255, CV_THRESH_BINARY | CV_THRESH_OTSU);
                    img = toBufferedImage(ilpImage2);
                    break;
            }
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Umbralización de OTSU(OpenCV).");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_OTSUOpenCVActionPerformed

    private void TrianguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrianguloActionPerformed
        if (img != null){
            IplImage ilpImage2;
            IplImage ilpImageGray;
            ColorModel cm = img.getColorModel();
            bitspp = cm.getPixelSize();
            switch(bitspp){
                case 24:
                    ilpImage2 = toIplImage(img);
                    ilpImageGray = cvCreateImage(cvGetSize(ilpImage2), IPL_DEPTH_8U, 1);
                    cvCvtColor(ilpImage2, ilpImageGray, CV_RGB2GRAY);
                    cvThreshold(ilpImageGray, ilpImageGray, 0, 255, CV_THRESH_BINARY | CV_THRESH_TRIANGLE );
                    img = toBufferedImage(ilpImageGray);
                    break;
                case 8:
                    ilpImage2 = toIplImage(img);
                    cvThreshold(ilpImage2, ilpImage2, 0, 255, CV_THRESH_BINARY | CV_THRESH_TRIANGLE );
                    img = toBufferedImage(ilpImage2);
                    break;
                default:
                    break;
            }
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Umbralización de Triangulo.");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_TrianguloActionPerformed

    private void OTSUPropioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OTSUPropioActionPerformed
        if (img != null){
            IplImage ilpImage2 ;
            IplImage ilpImageGray;
            ColorModel cm = img.getColorModel();
            bitspp = cm.getPixelSize();
            switch(bitspp){
                case 24:
                    ilpImage2 = toIplImage(img);
                    ilpImageGray = cvCreateImage(cvGetSize(ilpImage2), IPL_DEPTH_8U, 1);
                    cvCvtColor(ilpImage2, ilpImageGray, CV_RGB2GRAY);
                    img = toBufferedImage( ilpImageGray );
                    Histogram hist = new Histogram();
                    hist.setBins(img, "GRAY");
                    int[] histValues = hist.getGrayHistogram();
                    int t = OTSUThreshold(histValues, height*width);
                    img = myFilters.ThresholdBlackAndWhite(img, t);
                    break;
                case 8:
                    Histogram hist2 = new Histogram();
                    hist2.setBins(img, "GRAY");
                    //hist.normalizeHistograms("GRAY");
                    int[] histValues2 = hist2.getGrayHistogram();
                    int t2 = OTSUThreshold(histValues2, height*width);
                    img = myFilters.ThresholdBlackAndWhite(img, t2);
                    break;
                default:
                    break;
            }
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Umbralización de OTSU(Propio).");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_OTSUPropioActionPerformed

    private void ReduccionBitsPorPixelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReduccionBitsPorPixelActionPerformed
        if (img != null){
            SpinnerNumberModel model1 = new SpinnerNumberModel(4, 1, 7, 1);  // Initial value, min, max, step
            JSpinner spinBits = new JSpinner(model1);
            JLabel labelBits = new JLabel("Bits por canal :");
            JPanel panel1 = new JPanel();
            panel1.add(labelBits);
            panel1.add(spinBits);
            JCheckBox indexingButton = new JCheckBox("Indexar imagen.");

            Object[] params = {panel1, indexingButton};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog(  ScrollPanePanel,
                params,
                "Opciones de reducción de bits por pixel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title
            if (result == JOptionPane.NO_OPTION){
                return;
            }
            if(bitspp == 24){
                img = myFilters.ReduceColorsBitsPerChannel(img, 8 - (int)spinBits.getValue());
                boolean indexing = indexingButton.isSelected();
                if(indexing){
                    BufferedImage indexedImage = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                    Graphics2D g = indexedImage.createGraphics();
                    g.drawImage(img, 0,0,null);
                    img=indexedImage;
                }
                ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
                refreshImageDisplayed(true);
                refreshImageInformation("Aplicando Reduccion de colores.");
            }else{
                JOptionPane.showMessageDialog(this, "¡ERROR: Solo se pueden reducir imágenes de 24 bits");
            }  
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_ReduccionBitsPorPixelActionPerformed

    private void ErosionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ErosionActionPerformed
        if (img != null){
            IplConvKernel element;
            int w;
            int h;
            //Jpanel for the button groups
            JPanel optionPanel = new JPanel();
            JPanel sizePanel = new JPanel();
            JRadioButton def = new JRadioButton("Default");
            JRadioButton custom = new JRadioButton("Personalizado");
            JRadioButton s3 = new JRadioButton("3");
            JRadioButton s5 = new JRadioButton("5");
            JRadioButton s7 = new JRadioButton("7");
            ButtonGroup optionGroup = new ButtonGroup();
            ButtonGroup sizeGroup = new ButtonGroup();

            //adding to groups
            optionGroup.add(def);
            optionGroup.add(custom);
            sizeGroup.add(s3);
            sizeGroup.add(s5);
            sizeGroup.add(s7);
            s5.setSelected(true); 
            //adding to panels
            optionPanel.add(def);
            optionPanel.add(custom);
            def.setSelected(true);
            sizePanel.add(s3);
            sizePanel.add(s5);
            sizePanel.add(s7);

            Object[] params = {"Elemento Estructurante:", optionPanel, "En caso de ser personalizado, indique ancho y alto del elemento estructurante:", sizePanel};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog( ScrollPanePanel,
                params,
                "Opciones del Elemento Estructurante",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title

            String opt = getSelectedButtonText(optionGroup);

            if (result == JOptionPane.NO_OPTION){
                // If the kernel is 1x1 the image ends up the same or if the user cancels the action
                // return at once.
                return;
            }

            switch(opt){
                case "Default":
                    //It uses a rectangular structuring element by default
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
                    break;
                case "Personalizado":
                    String size = getSelectedButtonText(sizeGroup);
                    w = h = Integer.parseInt(size);
                    element = generateCustomStructuringEl(w, h);
                    break;
                default:
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
            }

            IplImage ilpImage2 = toIplImage(img);
            cvErode(ilpImage2, ilpImage2, element, 1);
            img = toBufferedImage(ilpImage2);
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Erosión");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_ErosionActionPerformed

    private void AperturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AperturaActionPerformed
        if (img != null){
            IplConvKernel element;
            int w;
            int h;
            //Jpanel for the button groups
            JPanel optionPanel = new JPanel();
            JPanel sizePanel = new JPanel();
            JRadioButton def = new JRadioButton("Default");
            JRadioButton custom = new JRadioButton("Personalizado");
            JRadioButton s3 = new JRadioButton("3");
            JRadioButton s5 = new JRadioButton("5");
            JRadioButton s7 = new JRadioButton("7");
            ButtonGroup optionGroup = new ButtonGroup();
            ButtonGroup sizeGroup = new ButtonGroup();

            //adding to groups
            optionGroup.add(def);
            optionGroup.add(custom);
            def.setSelected(true);
            sizeGroup.add(s3);
            sizeGroup.add(s5);
            sizeGroup.add(s7);
            s5.setSelected(true); 
            //adding to panels
            optionPanel.add(def);
            optionPanel.add(custom);
            sizePanel.add(s3);
            sizePanel.add(s5);
            sizePanel.add(s7);

            Object[] params = {"Elemento Estructurante:", optionPanel, "En caso de ser personalizado, indique ancho y alto del elemento estructurante:", sizePanel};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog( ScrollPanePanel,
                params,
                "Opciones del Elemento Estructurante",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title

            String opt = getSelectedButtonText(optionGroup);

            if (result == JOptionPane.NO_OPTION){
                // If the kernel is 1x1 the image ends up the same or if the user cancels the action
                // return at once.
                return;
            }

            switch(opt){
                case "Default":
                    //It uses a rectangular structuring element by default
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
                    break;
                case "Personalizado":
                    String size = getSelectedButtonText(sizeGroup);
                    w = h = Integer.parseInt(size);
                    element = generateCustomStructuringEl(w, h);
                    break;
                default:
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
            }

            IplImage temp = new IplImage();
            IplImage ilpImage2 = toIplImage(img);
            cvMorphologyEx(ilpImage2, ilpImage2, temp, element, CV_MOP_OPEN, 1);
            img = toBufferedImage(ilpImage2);
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Apertura");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_AperturaActionPerformed

    private void CierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CierreActionPerformed
        if (img != null){
            IplConvKernel element;
            int w;
            int h;
            //Jpanel for the button groups
            JPanel optionPanel = new JPanel();
            JPanel sizePanel = new JPanel();
            JRadioButton def = new JRadioButton("Default");
            JRadioButton custom = new JRadioButton("Personalizado");
            JRadioButton s3 = new JRadioButton("3");
            JRadioButton s5 = new JRadioButton("5");
            JRadioButton s7 = new JRadioButton("7");
            ButtonGroup optionGroup = new ButtonGroup();
            ButtonGroup sizeGroup = new ButtonGroup();
            
            //adding to groups
            optionGroup.add(def);
            optionGroup.add(custom);
            def.setSelected(true);

            sizeGroup.add(s3);
            sizeGroup.add(s5);
            sizeGroup.add(s7);
            s5.setSelected(true);

            //adding to panels
            optionPanel.add(def);
            optionPanel.add(custom);

            sizePanel.add(s3);
            sizePanel.add(s5);
            sizePanel.add(s7);

            Object[] params = {"Elemento Estructurante:", optionPanel, "En caso de ser personalizado, indique ancho y alto del elemento estructurante:", sizePanel};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog( ScrollPanePanel,
                params,
                "Opciones del Elemento Estructurante",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title

            String opt = getSelectedButtonText(optionGroup);

            if (result == JOptionPane.NO_OPTION){
                // If the kernel is 1x1 the image ends up the same or if the user cancels the action
                // return at once.
                return;
            }

            switch(opt){
                case "Default":
                    //It uses a rectangular structuring element by default
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
                    break;
                case "Personalizado":
                    String size = getSelectedButtonText(sizeGroup);
                    w = h = Integer.parseInt(size);
                    element = generateCustomStructuringEl(w, h);
                    break;
                default:
                    element = cvCreateStructuringElementEx(5, 5, 2, 2, CV_SHAPE_RECT);
            }

            IplImage temp = new IplImage();
            IplImage ilpImage2 = toIplImage(img);
            cvMorphologyEx(ilpImage2, ilpImage2, temp, element, CV_MOP_CLOSE, 1);
            img = toBufferedImage(ilpImage2);
            ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
            refreshImageDisplayed(true);
            refreshImageInformation("Aplicando Cierre");
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_CierreActionPerformed

    private void PaletaAleatoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaletaAleatoriaActionPerformed
        if (img != null){
            SpinnerNumberModel model1 = new SpinnerNumberModel(16, 2, 128, 1);  // Initial value, min, max, step
            JSpinner spinK = new JSpinner(model1);
            JLabel labelK = new JLabel("Número de Colores:");
            JPanel panel1 = new JPanel();
            JCheckBox indexingButton = new JCheckBox("Indexar imagen.");
            panel1.add(labelK);
            panel1.add(spinK);

            Object[] params = {panel1, indexingButton};
            Object[] options = {"Aceptar", "Cancelar"};
            int result = JOptionPane.showOptionDialog(  ScrollPanePanel,
                params,
                "Opciones de Paleta Aleatoria",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,           // Don't use a custom Icon
                options,        // The strings of buttons
                options[0]);    // Default button title
            if (result == JOptionPane.NO_OPTION){
                return;
            }

            if(bitspp == 24){
                if(colorsCounter > (int)spinK.getValue()){
                    // First we obtain a random color palette from the image with a certain number of colors.
                    int[] colorPalette = randomPalette((int)spinK.getValue());
                    // then we quantize the colors of the image according to the nearest color in the palette per pixel.
                    img = euclideanDistanceColorQuantization(colorPalette);
                    boolean indexing = indexingButton.isSelected();
                    if(indexing){
                        BufferedImage indexedImage = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                        Graphics2D g = indexedImage.createGraphics();
                        g.drawImage(img, 0,0,null);
                        img=indexedImage;
                    }
                    ImageOperationDequeSetting(UndoDeque, RedoDeque, img);
                    refreshImageDisplayed(true);
                    refreshImageInformation("Aplicando Reduccion de colores.");
                }
            }else{
                JOptionPane.showMessageDialog(this, "¡ERROR: Solo se pueden reducir imágenes de 24 bits");
            }  
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_PaletaAleatoriaActionPerformed

    private void DeshacerOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeshacerOperacionActionPerformed
        if (img != null){
            if(UndoDeque.size() <= 1){
                JOptionPane.showMessageDialog(this, "ERROR: No se puede deshacer");
                return;
            }
            BufferedImage imgTemp = UndoDeque.pop();
            try {                
                RedoDeque.push(imgTemp);
                img = UndoDeque.peek();
                refreshImageDisplayed(true);
                refreshImageInformation("Deshaciendo operación");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERROR: No se puede deshacer");
                UndoDeque.push(imgTemp);
                img = imgTemp;
            }
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_DeshacerOperacionActionPerformed

    private void RehacerOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RehacerOperacionActionPerformed
        if (img != null){ 
            if(RedoDeque.isEmpty()){
                JOptionPane.showMessageDialog(this, "ERROR: No se puede rehacer");
                return;
            }
            try {
                BufferedImage imgTemp = RedoDeque.pop();
                try {                    
                    img = imgTemp;
                    UndoDeque.push(imgTemp);
                    refreshImageDisplayed(true);
                    refreshImageInformation("Rehaciendo operación");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR: No se puede rehacer");
                    img = imgTemp;
                }
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(this, "ERROR: No se puede rehacer");
            }
        }else{
            JOptionPane.showMessageDialog(this, "¡ERROR: Cargue una imagen primero!");
        }
    }//GEN-LAST:event_RehacerOperacionActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainInterface().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem About;
    private javax.swing.JMenuItem AbrirArchivo;
    private javax.swing.JMenuItem Apertura;
    private javax.swing.JLabel BPP;
    private javax.swing.JPanel BarraEstadoPanel;
    private javax.swing.JMenuItem BlancoNegro;
    private javax.swing.JMenuItem Cierre;
    private javax.swing.JMenu ColorMenu;
    private javax.swing.JLabel Colores;
    private javax.swing.JMenuItem DeshacerOperacion;
    private javax.swing.JMenuItem Dilatacion;
    private javax.swing.JLabel Dimensiones;
    private javax.swing.JMenuItem Erosion;
    private javax.swing.JMenuItem EscalaDeGrises;
    private javax.swing.JLabel Estado;
    private javax.swing.JMenuItem GuardarImagen;
    private javax.swing.JPanel ImageInfoPanel;
    private javax.swing.JLabel InfoLabel;
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JMenu MenuArchivo;
    private javax.swing.JMenu MenuAyuda;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu MenuFiltros;
    private javax.swing.JMenu MorfologiaMenu;
    private javax.swing.JMenuItem OTSUOpenCV;
    private javax.swing.JMenuItem OTSUPropio;
    private javax.swing.JMenuItem PaletaAleatoria;
    private javax.swing.JMenuItem Readme;
    private javax.swing.JMenuItem ReduccionBitsPorPixel;
    private javax.swing.JMenu ReduccionColorMenu;
    private javax.swing.JMenuItem RehacerOperacion;
    private javax.swing.JPanel ScrollPanePanel;
    private javax.swing.JMenuItem Triangulo;
    private javax.swing.JMenu UmbralizacionMenu;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
