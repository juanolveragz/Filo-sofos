
import java.util.Random;
import java.util.concurrent.locks.Lock;
/**
 *Juan Carlos Olvera González 151408 juan.olveragz@udlap.mx
 * Gelio Castro Gracida 150604 gelio.castrogd@udlap.mx
 */
class Filosofo implements Runnable {
	// Tiempo en que come un filósofo
	private Random numGenerator = new Random();
	// El Id del filosofo
	private int id;

	/* 
	 * Definir los cubiertos del filosofo.
	 * En Java los recursos compartidos (tenedores) se definen como Locks.
	 */
	
	private Lock cubiertoIzquierdo;
	private Lock cubiertoDerecho;

	/**
	 * El constructor del filósofto
	 * @param id el id del filósofo
	 * @param cubiertoIzquierdo cubierto izquierdo
	 * @param cubiertoDerecho cubierto derecho
	 */
	public Filosofo (int id, Lock cubiertoIzquierdo, Lock cubiertoDerecho) {
		this.id = id;
		this.cubiertoIzquierdo = cubiertoIzquierdo;
		this.cubiertoDerecho = cubiertoDerecho;
	}

	/**
	 * Ejecuta el thread, piensa, levanta cubiertos, come.
	 */
	public void run() {
		try {
			while (true) {
				piensa();
				levantaCubiertoIzquierdo();
				levantaCubiertoDerecho();
				come();
				bajaCubiertos();
			}
		} catch (InterruptedException e) {
			System.out.println("El Filósofo " + id + " fue interrumpido.\n");			
		}
	}

	/** 
	 * El filósofo está pensando
	 */
	private void piensa() throws InterruptedException {
		System.out.println("El Filósofo " + id + " está pensando.\n");
		
		/*
		 * Cuando el filósofo está pensando debemos "dormir el hilo por un tiempo aleatorio"
		 *
		 */
		
		System.out.flush();
		Thread.sleep (numGenerator.nextInt(10));
	}

	/** 
	 * El filósofo ocupa el cubierto (Recurso compartido)
	 */
	private void levantaCubiertoIzquierdo() {
		cubiertoIzquierdo.lock();
		System.out.println("El filósofo " + id + " tiene en la mano un cubierto.\n");
	}

	/** 
	 * El filósofo ocupa el cubierto (Recurso compartido)
	 */
	private void levantaCubiertoDerecho() {
		/*
		 * Cuando el filósofo levanta el cubierto derecho el recurso es ocupado "Locked".
		 */
		cubiertoDerecho.lock();
	}

	/**
	 * El filósofo come...
	 * @throws InterruptedException
	 */
	private void come() throws InterruptedException {
		System.out.println("El filósofo " + id + " está comiendo.\n");
		Thread.sleep (numGenerator.nextInt(10));//Tiempo aleatorio en el que come el filosofo
	}

	/**
	 * El filósofo baja los cubiertos
	 */
	private void bajaCubiertos() {
		/*
		 * Cuando el filósofo deja de comer, este desocupa
		 * ambos cubiertos los recursos deben ser desbloqueados "Unlocked".
		 */
		cubiertoIzquierdo.unlock();
		cubiertoDerecho.unlock();
		
	}
}