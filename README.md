# CRUD MySQL usando JDBC

Projeto desenvolvido para treinamento para prova prática do SERPRO.

O programa consiste de métodos cadastrarVendedor, findAll e deleteById.

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