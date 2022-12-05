import { Avatar, Group, Modal, Text, UnstyledButton } from "@mantine/core";
import { IconChevronRight } from '@tabler/icons';
import { forwardRef } from "react";
import { User, UserEnum, WishlistItemType } from "../../types";

interface ButtonProps {
    icon?: React.ReactNode,
    image?: string
}

type WishlistButtonProps = ButtonProps & {
  onClick: React.MouseEventHandler,
  children: React.ReactNode,
}

const WishlistButton = forwardRef<HTMLButtonElement, WishlistButtonProps>(
    ({ onClick, children, icon, image, ...others }: WishlistButtonProps, ref) => (
      <UnstyledButton
        onClick={onClick}
        ref={ref}
        sx={(theme) => ({
          display: 'block',
          width: '100%',
          padding: theme.spacing.lg,
          color: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.white,
          backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[0] : theme.colors.dark[3],
          borderWidth: 1,
          borderStyle: 'solid',
          borderRadius: theme.radius.md,
          borderColor: theme.colorScheme === 'dark' ? theme.colors.dark[3] : theme.colors.gray[3],
          '&:hover': {
            backgroundColor:
              theme.colorScheme === 'dark' ? theme.colors.dark[2] : theme.colors.dark[6],
          },
        })}
        {...others}
      >
        {children}
      </UnstyledButton>
    )
  );

export default WishlistButton