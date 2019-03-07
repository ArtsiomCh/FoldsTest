package com.github.artsiomch.commentfolder;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.lang.folding.NamedFoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FoldsTestFoldingBuilder implements FoldingBuilder {

  final FoldingGroup group = FoldingGroup.newGroup("Folding group 1");

  @NotNull
  @Override
  public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
    return PsiTreeUtil.findChildrenOfType(
            PsiTreeUtil.findChildOfType(node.getPsi(), PsiImportList.class),
            PsiImportStatement.class)
        .stream()
        .skip(2)
        .map(this::createFoldingDescriptor)
        .toArray(FoldingDescriptor[]::new);
  }

  @NotNull
  private NamedFoldingDescriptor createFoldingDescriptor(PsiElement element) {
    return new NamedFoldingDescriptor(
        element.getNode(), element.getTextRange(), group, group.toString());
  }

  @Nullable
  @Override
  public String getPlaceholderText(@NotNull ASTNode node) {
    return null;
  }

  @Override
  public boolean isCollapsedByDefault(@NotNull ASTNode node) {
    return true;
  }
}
