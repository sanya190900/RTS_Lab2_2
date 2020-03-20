package com;

import java.util.Random;

public class Sinusoid {
    private int amountOfSinusoids; // кількість синусоїд
    private int frequency; // частота
    private int amountOfPoints; // кількість точок
    private int id;//змінна для виведення графіків, використання описано у класі Main
    private double[] x; // проміжний масив для результатів
    /*
     * Конструктор класу Sinusoid
     */
    public Sinusoid(int amountOfSinusoids, int frequency, int amountOfPoints) {
        this.amountOfSinusoids = amountOfSinusoids;
        this.frequency = frequency;
        this.amountOfPoints = amountOfPoints;
        this.x = new double[amountOfPoints];
        this.id = Main.id;
    }
    /*
     * Метод, що викликається з Main для побудови графіків
     * Викликає метод для обчислення функції швидкого перетворення Фур'є, яка повертає
     * fft_final, fft_real, fft_imagine залежно від параметра id
     */
    public double[] calculateFFTArray(){
        return calculateFFTArray(getSygnalsForResultingGarmonic(), id);
    }
    /*
     * Метод для обчислення функції швидкого перетворення Фур'є
     * У якості параметрів надходять масив х та ідентифікатор id
     */
    public double[] calculateFFTArray(double[] sygnalsOfResultingGarmonic, int id){
        int N = sygnalsOfResultingGarmonic.length;
        double[] array = new double[N];
        double[] fpRealPart1 = new double[N/2];
        double[] fpRealPart2 = new double[N/2];

        double[] fpImaginePart1 = new double[N/2];
        double[] fpImaginePart2 = new double[N/2];

        double[] fft_real = new double[N];
        double[] fft_imagine = new double[N];

        double[] fft_final = new double[N];

        for (int p = 0; p < N/2; p++) {
            for (int m = 0; m < N/2; m++) {
                fpRealPart1[p] += x[2 * m + 1] * Math.cos(4 * Math.PI / N * p * m);
                fpImaginePart1[p] += x[2 * m + 1] * Math.sin(4 * Math.PI / N * p * m);
                fpRealPart2[p] += x[2 * m] * Math.cos(4 * Math.PI / N * p * m);
                fpImaginePart2[p] += x[2 * m] * Math.sin(4 * Math.PI / N * p * m);
            }
            fft_real[p] = fpRealPart2[p] + fpRealPart1[p] * Math.cos(2 * Math.PI / N * p) - fpImaginePart1[p] * Math.sin(
                    2 * Math.PI / N * p);
            fft_imagine[p] = fpImaginePart2[p] + fpImaginePart1[p] * Math.cos(2 * Math.PI / N * p) + fpRealPart1[p] * Math.sin(
                    2 * Math.PI / N * p);
            fft_real[p + (N / 2)] = fpRealPart2[p] - (
                    fpRealPart1[p] * Math.cos(2 * Math.PI / N * p) - fpImaginePart1[p] * Math.sin(2 * Math.PI / N * p));
            fft_imagine[p + (N / 2)] = fpImaginePart2[p] - (
                    fpImaginePart1[p] * Math.cos(2 * Math.PI / N * p) + fpRealPart1[p] * Math.sin(2 * Math.PI / N * p));
            fft_final[p] = Math.sqrt(Math.pow(fft_real[p],2) + Math.pow(fft_imagine[p],2));
            fft_final[p + (N / 2)] = Math.sqrt(Math.pow(fft_real[p + (N / 2)],2) + Math.pow(fft_imagine[p + (N / 2)],2));
        }
        if (id == 1) array =  fft_final;
        else if (id == 2) array =  fft_real;
        else if (id == 3) array =  fft_imagine;
        return array;
    }
    /*
     * Метод для генерації випадкового сигналу
     */
    public double[] genRandomSignal() {
        Random r = new Random();
        double A = r.nextDouble();
        double q = r.nextDouble();
        for (int i = 0; i < amountOfPoints; i++) {
            for (int j = 0; j < amountOfSinusoids; j++) {
                x[i] += A * Math.sin(1.*frequency*(i+1)/amountOfSinusoids*j + q);
            }
        }
        return x;
    }
    /*
     * Метод для отримання кількості точок
     * @return - amountOfPoints
     */
    public int getCountOfPoints() {
        return amountOfPoints;
    }
    /*
     * Метод для отримання масиву х
     * @return - х
     */
    public double[] getSygnalsForResultingGarmonic() {
        return x;
    }
}