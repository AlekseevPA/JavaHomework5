package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {

	public static float[][] getValues() { // для формирования матрицы
		int n = 3;
		int m = 3;
		float[][] mx = new float[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				mx[i][j] = i * n + j + 1;
			}
		}

		return mx;
	}

	public static void main(String[] args) throws Exception {

		// 6.1
		Class<?> mx = Class.forName(Matrix.class.getName());
		Constructor<?> overridedConstructor = mx.getConstructor(int.class, int.class);
		Constructor<?> defaultConstructor = mx.getConstructor();

		// 6.2
		Matrix overridedMatrix = (Matrix) overridedConstructor.newInstance(3, 3);
		Matrix defaultMatrix = (Matrix) defaultConstructor.newInstance();

		overridedMatrix.setMatrix(getValues());

		// 6.3
		Method[] methods = mx.getDeclaredMethods();
		Field[] fields = mx.getDeclaredFields();

		for (Method m : methods) {
			System.out.println("метод " + m.toGenericString());
		}

		for (Field f : fields) {
			System.out.println("поле " + f.toGenericString());
		}

		// 6.4

		System.out.println(overridedMatrix);
		System.out.println(defaultMatrix);

		Method add = mx.getDeclaredMethod("add", Matrix.class);
		Method sub = mx.getDeclaredMethod("substract", Matrix.class);

		add.invoke(overridedMatrix, overridedMatrix);

		System.out.println(overridedMatrix);

		sub.invoke(overridedMatrix, overridedMatrix);

		System.out.println(overridedMatrix);

		// 6.5
		Method toString = mx.getDeclaredMethod("_toString");
		toString.setAccessible(true);
		System.out.println(toString.invoke(overridedMatrix));

	}

}
