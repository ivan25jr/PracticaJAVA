package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dao.DaoManager;
import Modelos.Jugadores;
import mySQL.MysqlManager;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaJugadores extends JFrame {

	private JPanel contentPane;
	private DaoManager manager;
	private JugadoresTableModel modelo;
	private JTable tabla;
	private JButton btnEditar;
	private JToolBar toolBar;
	private JButton btnA�adir;
	private JButton btnBorrar;
	private JButton btnGuardar;
	private JButton btnCancelar;
	public DetalleAlumnoPanel dj= new DetalleAlumnoPanel();
	public ListaJugadores(DaoManager manager) throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.manager= manager;
		//con esto se crea la tabla
		this.modelo=new JugadoresTableModel(manager.getJugador());
		this.modelo.updateModel();
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnA�adir = new JButton("anadir");
		btnA�adir.addActionListener(new BtnA�adirActionListener());
		toolBar.add(btnA�adir);
		
		 btnEditar = new JButton("editar");
		 btnEditar.addActionListener(new BtnEditarActionListener());
		btnEditar.setEnabled(false);
		toolBar.add(btnEditar);
		
		 btnBorrar = new JButton("borrar");
		 btnBorrar.addActionListener(new BtnBorrarActionListener());
		btnBorrar.setEnabled(false);
		toolBar.add(btnBorrar);
		
		 btnGuardar = new JButton("guardar");
		 btnGuardar.addActionListener(new BtnGuardarActionListener());
		btnGuardar.setEnabled(false);
		toolBar.add(btnGuardar);
		
		btnCancelar = new JButton("cancelar");
		btnCancelar.addActionListener(new BtnCancelarActionListener());
		btnCancelar.setEnabled(false);
		toolBar.add(btnCancelar);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		tabla = new JTable();
		getContentPane().add(tabla, BorderLayout.WEST);
		tabla.setModel(modelo);
		
		this.tabla.getSelectionModel().addListSelectionListener(e ->{
			boolean seleccionvalida= (tabla.getSelectedRow()!=-1);
			btnEditar.setEnabled(seleccionvalida);;
			btnBorrar.setEnabled(seleccionvalida);
		});
		
		getContentPane().add(dj, BorderLayout.CENTER);
		dj.setLayout(new BorderLayout(0, 0));
					
				
	}
	
	private class BtnA�adirActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dj.setJugador(null);
			dj.cargarDatos();
			dj.setEditable(true);
			btnGuardar.setEnabled(true);
			btnCancelar.setEnabled(true);
		}
	}
	
	private class BtnEditarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				Jugadores jugador = getJugadorSeleccionado();
				dj.setJugador(jugador);
				dj.setEditable(true);
				dj.cargarDatos();
				btnGuardar.setEnabled(true);
				btnCancelar.setEnabled(true);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class BtnCancelarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dj.setJugador(null);
			dj.setEditable(false);
			dj.cargarDatos();
			tabla.clearSelection();
			btnGuardar.setEnabled(false);
			btnCancelar.setEnabled(false);
		}
	}
	private class BtnBorrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
		
		try {
			Jugadores jugador= getJugadorSeleccionado();
			manager.getJugador().eliminar(jugador.getId());
			modelo.updateModel();
			modelo.fireTableDataChanged();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
	}
	private class BtnGuardarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dj.guardarDatos();
			Jugadores jugador =dj.getJugador();
			try {
				if(jugador.getId()==null) {
				
					
						manager.getJugador().insertar(jugador);
					
				
			}else {
				
					manager.getJugador().modificar(jugador);
				
				}
				}catch (Exception e) {
					// TODO: handle exception
				}
			
			dj.setJugador(null);
			dj.setEditable(false);
			dj.cargarDatos();
			tabla.clearSelection();
			btnGuardar.setEnabled(false);
			btnCancelar.setEnabled(false);
			modelo.updateModel();
			modelo.fireTableDataChanged();
			
		}
	}
	private Jugadores getJugadorSeleccionado() throws ClassNotFoundException, SQLException {
		Integer id= (Integer) tabla.getValueAt(tabla.getSelectedRow(), 0);
		return manager.getJugador().buscar(id);
	}


	public static void main(String[] args) {
		DaoManager manager = new MysqlManager();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					new ListaJugadores(manager).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
