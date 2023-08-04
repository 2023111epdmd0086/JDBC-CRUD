package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.example.db.DB;
import com.example.db.DbException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        Connection conn = null;
        Statement st = null;

        try {
            conn = DB.getConnection();
            st = conn.createStatement();

            System.out.println("================= SELECIONE O QUE DESEJA: =================");
            System.out.println("1 - Listar todos vendedores");
            System.out.println("2 - Cadastrar vendedor");
            System.out.println("3 - Deletar vendedor");
            System.out.println();
            int opc = sc.nextInt();
            sc.nextLine();
            System.out.println();
            switch (opc) {
                case 1:
                    findAll(st);
                    break;

                case 2:
                    cadastrarVendedor(sc, sdf, sdf2, st);
                    break;

                case 3:
                    deleteById(sc, st);
                    break;

                default:
                    System.out.println("Opção inválida. Rode o programa novamente!!!!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (ParseException e) {
            throw new DbException(e.getMessage());
        } finally {
            sc.close();
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
            DB.closeConnection();
        }

    }

    private static void cadastrarVendedor(Scanner sc, SimpleDateFormat sdf, SimpleDateFormat sdf2, Statement st)
            throws ParseException, SQLException {
        System.out.println("================= CADASTRAR NOVO VENDEDOR NO BD =================");
        System.out.print("Digite o nome do vendedor: ");
        String name = sc.nextLine();
        System.out.print("Digite o email do vendedor: ");
        String email = sc.nextLine();
        System.out.print("Digite a data de nascimento do vendedor (dd/MM/yy): ");
        Date birthDate = sdf.parse(sc.nextLine());
        String dataFormatada = sdf2.format(birthDate);
        System.out.print("Digite o salário base do vendedor: ");
        double baseSalary = sc.nextDouble();
        sc.nextLine();
        System.out.print("Digite o departamento do vendedor: ");
        int departmentId = sc.nextInt();
        sc.nextLine();

        st.executeUpdate(
                "INSERT INTO seller "
                        + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                        + "VALUES ("
                        + "'" + name + "', "
                        + "'" + email + "', "
                        + "'" + dataFormatada + "', "
                        + "'" + String.format("%.2f", baseSalary) + "', "
                        + "'" + departmentId + "');"

        );

        System.out.println("Adicionado com sucesso o vendedor " + name);
    }

    private static void findAll(Statement st) throws SQLException {
        ResultSet rs;
        rs = st.executeQuery("select * from seller;");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
        }
    }

    private static void deleteById(Scanner sc, Statement st) throws SQLException {
        System.out.println("================= DELETAR VENDEDOR POR ID DO BD =================");
        System.out.print("Digite o ID do vendedor: ");
        int id = sc.nextInt();
        sc.nextLine();

        st.executeUpdate("DELETE FROM seller WHERE seller.id =" + id + ";");

        System.out.println("Adicionado com sucesso o vendedor de ID " + id);
    }
}
