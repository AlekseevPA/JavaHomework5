package reflection;

public class Matrix {
	public static Matrix IDENTITY = Matrix.identity(); // единичная матрица

	protected int n, m; // размерность
	protected float[][] matrix; // сама матрица

	public Matrix() { // конструктор по умолчанию
		n = m = 3; // задаем размер матрицы 3 х 3
		matrix = new float[n][m];
	}

	public Matrix(int n, int m) { // вспомогательный конструктор
		this.n = n; // задаем размер матрицы
		this.m = m;
		this.matrix = new float[n][m];
	}

	public Matrix add(Matrix m) throws Exception {
		if (this.m != m.m && this.n != m.n) { // если размер матриц не
												// совпадает
			throw new Exception("Матрицы должны быть одинакового размера");
		}

		Matrix result = new Matrix(m.n, m.m);

		for (int i = 0; i < m.n; i++) {
			for (int j = 0; j < m.m; j++) {
				result.matrix[i][j] = matrix[i][j] + m.matrix[i][j]; // суммируем
			}
		}

		return result;
	}

	public Matrix substract(Matrix m) throws Exception {
		if (this.m != m.m && this.n != m.n) { // если размер матриц не
												// совпадает
			throw new Exception("Матрицы должны быть одинакового размера");
		}

		Matrix result = new Matrix(m.n, m.m);

		for (int i = 0; i < m.n; i++) {
			for (int j = 0; j < m.m; j++) {
				result.matrix[i][j] = matrix[i][j] - m.matrix[i][j]; // вычитаем
			}
		}

		return result;
	}

	public Matrix mult(int value) { // умножение матрицы на число
		Matrix result = new Matrix(n, m);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				result.matrix[i][j] = matrix[i][j] * value;
			}
		}

		return result;
	}

	public float det() throws Exception {
		if (n != m) {
			throw new Exception("Матрицы должны быть одинакового размера");
		}

		return det(matrix, n);
	}

	public static float det(float[][] mt, int n) { // вычисляем детерминант

		float det = 0;
		if (n == 1) {
			det = mt[0][0];
		} else if (n == 2) {
			det = mt[0][0] * mt[1][1] - mt[1][0] * mt[0][1];
		} else {
			for (int j1 = 0; j1 < n; j1++) {
				float[][] m = new float[n - 1][];
				for (int k = 0; k < n - 1; k++) {
					m[k] = new float[n - 1];
				}
				for (int i = 1; i < n; i++) {
					int j2 = 0;
					for (int j = 0; j < n; j++) {
						if (j == j1)
							continue;
						m[i - 1][j2] = mt[i][j];
						j2++;
					}
				}
				det += Math.pow(-1.0, j1) * mt[0][j1] * det(m, n - 1);
			}
		}
		return det;
	}

	public Matrix inversion() throws Exception { // обратная матрица
		if (n != m) {
			throw new Exception("Матрицы должны быть одинакового размера");
		}

		return inversion(matrix, n);

	}

	public static Matrix inversion(Matrix m) throws Exception { // статический метод для
												// получения обратной
												// матрицы
		return m.inversion();
	}

	public static Matrix inversion(float[][] A, int N) { // вычисляем
															// обратную
															// матрицу
		double temp;

		float[][] E = new float[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				E[i][j] = 0f;

				if (i == j)
					E[i][j] = 1f;
			}

		for (int k = 0; k < N; k++) {
			temp = A[k][k];

			for (int j = 0; j < N; j++) {
				A[k][j] /= temp;
				E[k][j] /= temp;
			}

			for (int i = k + 1; i < N; i++) {
				temp = A[i][k];

				for (int j = 0; j < N; j++) {
					A[i][j] -= A[k][j] * temp;
					E[i][j] -= E[k][j] * temp;
				}
			}
		}

		for (int k = N - 1; k > 0; k--) {
			for (int i = k - 1; i >= 0; i--) {
				temp = A[i][k];

				for (int j = 0; j < N; j++) {
					A[i][j] -= A[k][j] * temp;
					E[i][j] -= E[k][j] * temp;
				}
			}
		}

		Matrix result = new Matrix(E.length, E.length);
		result.setMatrix(E);

		return result;
	}

	public static Matrix identity() { // возвращает еденичную матрицу
		Matrix m = new Matrix();

		for (int i = 0; i < m.n; i++) {
			for (int j = 0; j < m.m; j++) {
				m.matrix[i][j] = 0;
				if (i == j) {
					m.matrix[i][j] = 1;
				}
			}
		}

		return m;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Matrix %dx%d \n", n, m));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				sb.append(String.format("%7.2f", matrix[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private String _toString() {
		return "private call\n" + this.toString();
	}

	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}

	public float[][] getMatrix() {
		return matrix;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setM(int m) {
		this.m = m;
	}

	public void setMatrix(float[][] matrix) {
		this.matrix = matrix;
	}
}
