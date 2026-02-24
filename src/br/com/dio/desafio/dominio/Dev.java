package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev {

    private String nome;

    private final Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private final Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();

    public void inscreverBootcamp(Bootcamp bootcamp){
        // Validação defensiva
        if (bootcamp == null) {
            throw new IllegalArgumentException("O bootcamp não pode ser nulo.");
        }
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.getDevsInscritos().add(this);
    }

    public void progredir() {
        // MELHORIA: Uso de ifPresentOrElse ou lançamento de exceção
        this.conteudosInscritos.stream()
                .findFirst()
                .ifPresentOrElse(conteudo -> {
                    this.conteudosConcluidos.add(conteudo);
                    this.conteudosInscritos.remove(conteudo);
                }, () -> {
                    // Em vez de System.err, lançamos uma exceção de negócio
                    throw new NoSuchElementException("Você não está matriculado em nenhum conteúdo!");
                });
    }

    public double calcularTotalXp() {
        return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return Collections.unmodifiableSet(conteudosInscritos);
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return Collections.unmodifiableSet(conteudosConcluidos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dev)) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}