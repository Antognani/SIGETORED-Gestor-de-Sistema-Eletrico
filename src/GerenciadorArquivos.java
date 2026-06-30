import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivos {

    private final String EQUIPES = "equipes.txt";

    //SALVAR
    public void salvarEquipes(List<EquipeCampo> equipes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EQUIPES))) {

            for (EquipeCampo equipe : equipes) {
                String linha = equipe.getId() + ";" +
                        equipe.getNome() + ";" +
                        equipe.temTreinamentoLV() + ";" +
                        equipe.isOcupada();

                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Dados das equipes salvos com sucesso no arquivo " + EQUIPES);

        } catch (IOException e) {
            System.out.println("Erro ao tentar salvar o arquivo de equipes: " + e.getMessage());
        }
    }

    // CARREGAMENTO
    public List<EquipeCampo> carregarEquipes() {
        List<EquipeCampo> equipesCarregadas = new ArrayList<>();
        File arquivo = new File(EQUIPES);

        // se o arquivo não existir (primeira vez rodando), retorna lista vazia
        if (!arquivo.exists()) {
            return equipesCarregadas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                boolean credencial = Boolean.parseBoolean(dados[2]);
                boolean ocupada = Boolean.parseBoolean(dados[3]);

                // recria o objeto e adiciona na lista
                EquipeCampo equipe = new EquipeCampo(id, nome, credencial);
                equipe.setOcupada(ocupada);

                equipesCarregadas.add(equipe);
            }
            System.out.println("Dados das equipes carregados com sucesso.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao ler o arquivo de equipes: " + e.getMessage());
        }
        return equipesCarregadas;
    }
}
