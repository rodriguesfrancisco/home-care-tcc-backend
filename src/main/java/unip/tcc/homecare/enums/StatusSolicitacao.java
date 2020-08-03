package unip.tcc.homecare.enums;

public enum StatusSolicitacao {
    EM_ABERTO("Em Aberto"),
    ANALISE("Em análise pelo paciente"),
    EM_EXECUCAO("Em Execução"),
    CONCLUIDA("Concluida");

    private String code;

    private StatusSolicitacao(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
