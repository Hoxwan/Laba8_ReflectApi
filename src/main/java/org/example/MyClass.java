
package org.example;

public class MyClass {
    @Repeat(2)
    public void publicMethod(String param) {
        System.out.println("Публичный метод: " + param);
    }

    @Repeat(3)
    protected void protectedMethod(String param) {
        System.out.println("Защищенный метод: " + param);
    }

    @Repeat(4)
    private void privateMethod(String param) {
        System.out.println("Приватный метод: " + param);
    }

    @Repeat(5)
    public void anotherPublicMethod(String param) {
        System.out.println("Другой публичный метод: " + param);
    }

    @Repeat(6)
    protected void anotherProtectedMethod(String param) {
        System.out.println("Другой защищенный метод: " + param);
    }

    @Repeat(7)
    private void anotherPrivateMethod(String param) {
        System.out.println("Другой приватный метод: " + param);
    }
}
