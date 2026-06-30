import java.util.ArrayList;
import java.util.List;

public class GerenciadorRede {

    // listas pra armazenar o estado do sistema na memória
    private List<AtivoRede> ativosRegistrados = new ArrayList<>();
    private List<EquipeCampo> equipesDisponiveis = new ArrayList<>();
    private List<OrdemServico> ordensAtivas = new ArrayList<>();

    // --- REGRA DE NEGÓCIO 1: Interação entre Transformador e Poste ---
    public void instalarTransformadorNoPoste(Transformador transformador, Poste poste) throws CapacidadeExcedidaException {

        // Regra: Trafos pesados (>75 kVA) precisam de postes  resistentes (>600 daN)
        if (transformador.getCargaKva() > 75.0 && poste.getEsforcoSuportado() <= 600.0) {
            //erro
            throw new CapacidadeExcedidaException("Risco de queda: o poste " + poste.getId() + " suporta apenas " + poste.getEsforcoSuportado() + " daN. Insuficiente para um transformador de " + transformador.getCargaKva() + " kVA.");
        }
        ativosRegistrados.add(transformador);
        System.out.println("Sucesso: trafo " + transformador.getId() + " instalado com segurança no poste " + poste.getId() + ".");
    }

    // --- REGRA DE NEGÓCIO 2: Interação entre Ordem de Serviço e Equipe ---
    public void despacharOS(OrdemServico os, EquipeCampo equipe) throws EquipeIndisponivelException {

        //só despacha equipe livre
        if (equipe.isOcupada()) {
            throw new EquipeIndisponivelException("Operação negada: a equipe '" + equipe.getNome() + "' já possui uma OS em execução.");
        }
        if (os.getServicoAssociado().exigeTreinamentoLV() && !equipe.temTreinamentoLV()) {
            throw new EquipeIndisponivelException("Bloqueio de segurança: a equipe '" + equipe.getNome() + "' não possui Treinamento LV para operar transformadores.");
        }
        os.despachar(equipe);
        ordensAtivas.add(os);
    }

    //metodos de apoio
    public void adicionarEquipe(EquipeCampo equipe) {
        equipesDisponiveis.add(equipe);
    }

    // metodo pro main listar e remover ewuipes
    public List<EquipeCampo> getEquipesDisponiveis() {
        return equipesDisponiveis;
    }

    public void adicionarPoste(Poste p) {
        ativosRegistrados.add(p);
    }

    public List<AtivoRede> getAtivosRegistrados() {
        return ativosRegistrados;
    }
}
