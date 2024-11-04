package Model;

public class Aluno {
    private String id;
    private String nome;
    private String curso;
    private String modalidade;
    private String status;

    // Construtor
    public Aluno(String id, String nome, String curso, String modalidade, String status) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.modalidade = modalidade;
        this.status = status;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Método para verificar se o aluno é do curso de "História" e na modalidade "presencial"
    public boolean isAlunoDeHistoriaPresencial() {
        return "História".equals(this.curso) && "presencial".equals(this.modalidade);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                ", modalidade='" + modalidade + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

